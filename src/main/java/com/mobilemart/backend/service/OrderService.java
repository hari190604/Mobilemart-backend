package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.OrderItemResponse;
import com.mobilemart.backend.dto.OrderRequest;
import com.mobilemart.backend.dto.OrderResponse;
import com.mobilemart.backend.dto.AddressResponse;
import com.mobilemart.backend.dto.PaymentVerificationRequest;
import com.mobilemart.backend.entity.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import com.mobilemart.backend.repository.CartItemRepository;
import com.mobilemart.backend.repository.OrderRepository;
import com.mobilemart.backend.repository.ProductRepository;
import com.mobilemart.backend.repository.UserRepository;
import com.mobilemart.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Transactional
    public ApiResponse placeOrder(String username, OrderRequest request) {
        if (request.getAddressId() == null) {
            return new ApiResponse(false, "Shipping address is required");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Address address = addressRepository.findByAddressIdAndUser_UserId(request.getAddressId(), user.getUserId()).orElse(null);
        if (address == null) {
            return new ApiResponse(false, "Shipping address not found or does not belong to user");
        }

        List<CartItem> cartItems = cartItemRepository.findByUser_UserId(user.getUserId());
        if (cartItems.isEmpty()) {
            return new ApiResponse(false, "Cart is empty. Cannot place order.");
        }

        // Verify stock for all items
        for (CartItem item : cartItems) {
            if (item.getProduct().getStock() < item.getQuantity()) {
                return new ApiResponse(false, "Product '" + item.getProduct().getName() + "' does not have enough stock.");
            }
        }

        // Generate custom Order ID
        String orderId = generateOrderId();

        Order order = new Order();
        order.setOrderId(orderId);
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus(OrderStatus.PENDING);
        
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            
            // Deduct stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerUnit(product.getPrice());
            
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            orderItem.setTotalPrice(itemTotal);
            
            totalAmount = totalAmount.add(itemTotal);
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        // Create Razorpay Order
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            JSONObject orderRequest = new JSONObject();
            // amount in paise
            orderRequest.put("amount", totalAmount.multiply(new BigDecimal("100")).intValue());
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", orderId);

            com.razorpay.Order razorpayOrder = razorpay.orders.create(orderRequest);
            order.setRazorpayOrderId(razorpayOrder.get("id"));
        } catch (RazorpayException e) {
            return new ApiResponse(false, "Failed to initialize payment gateway: " + e.getMessage());
        }

        Order savedOrder = orderRepository.save(order);
        
        // Clear user's cart
        cartItemRepository.deleteByUser_UserId(user.getUserId());

        return new ApiResponse(true, "Order placed successfully", mapToDto(savedOrder));
    }

    @Transactional
    public ApiResponse verifyPayment(String username, PaymentVerificationRequest request) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Order order = orderRepository.findByRazorpayOrderId(request.getRazorpayOrderId()).orElse(null);
        if (order == null) {
            return new ApiResponse(false, "Order not found");
        }

        if (!order.getUser().getUserId().equals(user.getUserId())) {
            return new ApiResponse(false, "Order does not belong to the user");
        }

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            boolean status = Utils.verifyPaymentSignature(options, razorpayKeySecret);
            if (status) {
                order.setStatus(OrderStatus.SUCCESS);
                order.setRazorpayPaymentId(request.getRazorpayPaymentId());
                order.setRazorpaySignature(request.getRazorpaySignature());
                orderRepository.save(order);
                return new ApiResponse(true, "Payment verified successfully", mapToDto(order));
            } else {
                return new ApiResponse(false, "Payment verification failed");
            }
        } catch (RazorpayException e) {
            return new ApiResponse(false, "Error verifying payment: " + e.getMessage());
        }
    }

    public ApiResponse getMyOrders(String username, int page, int size) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Order> orders = orderRepository.findByUser_UserId(user.getUserId(), pageable);
        
        Page<OrderResponse> responsePage = orders.map(this::mapToDto);
        return new ApiResponse(true, "Orders fetched successfully", responsePage);
    }
    
    public ApiResponse getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Order> orders = orderRepository.findAll(pageable);
        
        Page<OrderResponse> responsePage = orders.map(this::mapToDto);
        return new ApiResponse(true, "All orders fetched successfully", responsePage);
    }

    public ApiResponse updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return new ApiResponse(false, "Order not found");
        }

        Order order = orderOpt.get();
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            orderRepository.save(order);
            return new ApiResponse(true, "Order status updated successfully", mapToDto(order));
        } catch (IllegalArgumentException e) {
            return new ApiResponse(false, "Invalid status. Must be PENDING, SUCCESS, or FAILED.");
        }
    }

    private String generateOrderId() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // A simple random 4 digit sequence for demonstration
        int randomSeq = (int) (Math.random() * 9000) + 1000;
        return "ORD" + datePart + randomSeq;
    }

    private OrderResponse mapToDto(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream().map(item -> 
            OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getProductId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .pricePerUnit(item.getPricePerUnit())
                .totalPrice(item.getTotalPrice())
                .build()
        ).collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .shippingAddress(addressService.mapToDto(order.getShippingAddress()))
                .razorpayOrderId(order.getRazorpayOrderId())
                .items(itemResponses)
                .build();
    }
}
