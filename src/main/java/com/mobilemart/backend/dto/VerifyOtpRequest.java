package com.mobilemart.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {
    
    @NotBlank(message = "Email or Username is required")
    private String identifier;
    
    @NotBlank(message = "OTP is required")
    private String otp;
}
