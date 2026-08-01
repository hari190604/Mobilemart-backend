package com.mobilemart.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    
    @NotBlank(message = "Email or Username is required")
    private String identifier;
}
