package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.*;
import com.mobilemart.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        ApiResponse response = authService.registerUser(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            ApiResponse response = authService.loginUser(request);
            if (response.isSuccess() && response.getData() instanceof AuthResponse) {
                String jwt = ((AuthResponse) response.getData()).getToken();
                
                ResponseCookie authTokenCookie = ResponseCookie.from("authToken", jwt).path("/").maxAge(86400).sameSite("Lax").build();
                ResponseCookie tokenCookie = ResponseCookie.from("token", jwt).path("/").maxAge(86400).sameSite("Lax").build();
                ResponseCookie optinovaTokenCookie = ResponseCookie.from("optinova_token", jwt).path("/").maxAge(86400).sameSite("Lax").build();
                
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, authTokenCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, optinovaTokenCookie.toString())
                        .body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        ApiResponse response = authService.logoutUser(token);
        
        ResponseCookie clearAuthToken = ResponseCookie.from("authToken", "null").path("/").maxAge(86400).sameSite("Lax").build();
        ResponseCookie clearToken = ResponseCookie.from("token", "null").path("/").maxAge(86400).sameSite("Lax").build();
        ResponseCookie clearOptinova = ResponseCookie.from("optinova_token", "null").path("/").maxAge(86400).sameSite("Lax").build();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearAuthToken.toString())
                .header(HttpHeaders.SET_COOKIE, clearToken.toString())
                .header(HttpHeaders.SET_COOKIE, clearOptinova.toString())
                .body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        ApiResponse response = authService.generateOtp(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        ApiResponse response = authService.verifyOtp(request);
        if (response.isSuccess() && response.getData() instanceof AuthResponse) {
            String jwt = ((AuthResponse) response.getData()).getToken();
            
            ResponseCookie authTokenCookie = ResponseCookie.from("authToken", jwt).path("/").maxAge(86400).sameSite("Lax").build();
            ResponseCookie tokenCookie = ResponseCookie.from("token", jwt).path("/").maxAge(86400).sameSite("Lax").build();
            ResponseCookie optinovaTokenCookie = ResponseCookie.from("optinova_token", jwt).path("/").maxAge(86400).sameSite("Lax").build();
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, authTokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, optinovaTokenCookie.toString())
                    .body(response);
        } else if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Fallback for previous implementation compatibility if needed
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        ApiResponse response = authService.resetPassword(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        
        String username = authentication.getName();
        ApiResponse response = authService.changePassword(username, request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
