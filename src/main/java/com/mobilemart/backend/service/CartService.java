package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.CartItemRequest;
import com.mobilemart.backend.dto.CartItemResponse;
import com.mobilemart.backend.entity.CartItem;
import com.mobilemart.backend.entity.WishlistItem;
import com.mobilemart.backend.entity.Product;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.CartItemRepository;
import com.mobilemart.backend.repository.WishlistItemRepository;
import com.mobilemart.backend.repository.ProductRepository;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse addToCart(String username, CartItemRequest request) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return new ApiResponse(false, "Product not found");
        }

        if (product.getStock() < request.getQuantity()) {
            return new ApiResponse(false, "Not enough stock available");
        }

        // Check if item already in cart
        Optional<CartItem> existingItemOpt = cartItemRepository.findByUser_UserIdAndProduct_ProductId(user.getUserId(), product.getProductId());
        
        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            if (product.getStock() < newQuantity) {
                return new ApiResponse(false, "Cannot add more. Not enough stock available.");
            }
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        cartItemRepository.save(cartItem);
        return new ApiResponse(true, "Item added to cart successfully");
    }

    public ApiResponse getCart(String username) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        List<CartItem> items = cartItemRepository.findByUser_UserId(user.getUserId());
        
        List<CartItemResponse> responses = items.stream().map(item -> {
            String imageUrl = null;
            if (item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
                imageUrl = item.getProduct().getImages().get(0).getImageUrl();
            }
            
            return CartItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getProductId())
                    .productName(item.getProduct().getName())
                    .productImageUrl(imageUrl)
                    .price(item.getProduct().getPrice())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                    .build();
        }).collect(Collectors.toList());

        return new ApiResponse(true, "Cart fetched successfully", responses);
    }

    public ApiResponse removeFromCart(String username, Integer cartItemId) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemId);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (item.getUser().getUserId().equals(user.getUserId())) {
                cartItemRepository.delete(item);
                return new ApiResponse(true, "Item removed from cart");
            }
            return new ApiResponse(false, "Unauthorized to remove this item");
        }
        
        return new ApiResponse(false, "Cart item not found");
    }
    
    public ApiResponse clearCart(String username) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }
        
        cartItemRepository.deleteByUser_UserId(user.getUserId());
        return new ApiResponse(true, "Cart cleared successfully");
    }

    // --- Wishlist Methods ---

    public ApiResponse toggleWishlist(String username, Integer productId) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ApiResponse(false, "Product not found");
        }

        Optional<WishlistItem> existingItemOpt = wishlistItemRepository.findByUser_UserIdAndProduct_ProductId(user.getUserId(), productId);

        if (existingItemOpt.isPresent()) {
            wishlistItemRepository.delete(existingItemOpt.get());
            return new ApiResponse(true, "Item removed from wishlist");
        } else {
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setUser(user);
            wishlistItem.setProduct(product);
            wishlistItemRepository.save(wishlistItem);
            return new ApiResponse(true, "Item added to wishlist");
        }
    }

    public ApiResponse getWishlist(String username) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        List<WishlistItem> items = wishlistItemRepository.findByUser_UserId(user.getUserId());

        List<CartItemResponse> responses = items.stream().map(item -> {
            String imageUrl = null;
            if (item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
                imageUrl = item.getProduct().getImages().get(0).getImageUrl();
            }

            return CartItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getProductId())
                    .productName(item.getProduct().getName())
                    .productImageUrl(imageUrl)
                    .price(item.getProduct().getPrice())
                    .quantity(1) // Default to 1 for wishlist UI compatibility
                    .totalPrice(item.getProduct().getPrice())
                    .build();
        }).collect(Collectors.toList());

        return new ApiResponse(true, "Wishlist fetched successfully", responses);
    }

    public ApiResponse clearWishlist(String username) {
        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        wishlistItemRepository.deleteByUser_UserId(user.getUserId());
        return new ApiResponse(true, "Wishlist cleared successfully");
    }
}
