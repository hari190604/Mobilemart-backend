package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.CartItemRequest;
import com.mobilemart.backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(Authentication authentication, @Valid @RequestBody CartItemRequest request) {
        String username = authentication.getName();
        ApiResponse response = cartService.addToCart(username, request);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getCart(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(cartService.getCart(username));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> removeFromCart(Authentication authentication, @PathVariable Integer itemId) {
        String username = authentication.getName();
        ApiResponse response = cartService.removeFromCart(username, itemId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(cartService.clearCart(username));
    }

    // --- Wishlist Endpoints ---

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<ApiResponse> toggleWishlist(Authentication authentication, @PathVariable Integer productId) {
        String username = authentication.getName();
        ApiResponse response = cartService.toggleWishlist(username, productId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping("/wishlist")
    public ResponseEntity<ApiResponse> getWishlist(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(cartService.getWishlist(username));
    }

    @DeleteMapping("/wishlist/clear")
    public ResponseEntity<ApiResponse> clearWishlist(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(cartService.clearWishlist(username));
    }
}
