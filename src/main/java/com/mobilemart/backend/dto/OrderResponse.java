package com.mobilemart.backend.dto;

import com.mobilemart.backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private AddressResponse shippingAddress;
    private String razorpayOrderId;
    private List<OrderItemResponse> items;
}
