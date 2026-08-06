package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.UserAdminUpdateRequest;
import com.mobilemart.backend.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Users retrieved", adminUserService.getAllUsers()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to retrieve users"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUserStatus(@PathVariable Long id, @Valid @RequestBody UserAdminUpdateRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", adminUserService.updateUserStatus(id, request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
