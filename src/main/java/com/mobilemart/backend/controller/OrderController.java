package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.OrderRequest;
import com.mobilemart.backend.dto.PaymentVerificationRequest;
import com.mobilemart.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(Authentication authentication, @RequestBody OrderRequest request) {
        String username = authentication.getName();
        ApiResponse response = orderService.placeOrder(username, request);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<ApiResponse> verifyPayment(Authentication authentication, @RequestBody PaymentVerificationRequest request) {
        String username = authentication.getName();
        ApiResponse response = orderService.verifyPayment(username, request);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping
    public ResponseEntity<Object> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        Object response = orderService.getMyOrders(username);
        if (response == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(response);
    }
}
