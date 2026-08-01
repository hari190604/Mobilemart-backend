package com.mobilemart.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Long userId;
    private String username;
    private String fullName;
    private String mobileNumber;
    private String email;
}
