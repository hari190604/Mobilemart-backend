package com.mobilemart.backend.dto;

import com.mobilemart.backend.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAdminResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobileNumber;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
