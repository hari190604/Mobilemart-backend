package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.AddressRequest;
import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse> addAddress(Authentication authentication, @RequestBody AddressRequest request) {
        String username = authentication.getName();
        ApiResponse response = addressService.addAddress(username, request);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUserAddresses(Authentication authentication) {
        String username = authentication.getName();
        ApiResponse response = addressService.getUserAddresses(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAddress(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody AddressRequest request) {
        String username = authentication.getName();
        ApiResponse response = addressService.updateAddress(username, id, request);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(
            Authentication authentication,
            @PathVariable Long id) {
        String username = authentication.getName();
        ApiResponse response = addressService.deleteAddress(username, id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
}
