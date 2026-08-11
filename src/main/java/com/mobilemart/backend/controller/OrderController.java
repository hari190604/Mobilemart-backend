package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.OrderRequest;
import com.mobilemart.backend.dto.OrderResponse;
import com.mobilemart.backend.dto.PaymentVerificationRequest;
import com.mobilemart.backend.service.OrderService;
import com.mobilemart.backend.service.InvoicePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private InvoicePdfService invoicePdfService;

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
    public ResponseEntity<ApiResponse> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        ApiResponse response = orderService.getMyOrders(username);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(Authentication authentication, @PathVariable String orderId) {
        String username = authentication.getName();
        ApiResponse response = orderService.cancelOrder(username, orderId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping(value = "/{orderId}/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getOrderInvoiceDetails(Authentication authentication, @PathVariable String orderId) {
        String username = authentication.getName();
        ApiResponse response = orderService.getSecureOrderDetails(username, orderId);
        
        if (!response.isSuccess()) {
            return ResponseEntity.status(response.getMessage().startsWith("Unauthorized:") ? 403 : 404).body(null);
        }
        
        OrderResponse orderData = (OrderResponse) response.getData();
        byte[] pdfBytes = invoicePdfService.generateInvoice(orderData);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "MobileMart-Invoice-" + orderId + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.status(200).headers(headers).body(pdfBytes);
    }
}
