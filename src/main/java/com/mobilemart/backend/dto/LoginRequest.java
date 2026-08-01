package com.mobilemart.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    // Can be email or username
    @NotBlank(message = "Email or Username is required")
    private String identifier;
    
    @NotBlank(message = "Password is required")
    private String password;
}
