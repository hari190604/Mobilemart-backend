package com.mobilemart.backend.dto;

import com.mobilemart.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String accessToken;
    private String tokenType;
    private Long expiresInMs;
    private AuthUser user;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthUser {
        private Long userId;
        private String username;
        private String email;
        private Role role;
    }
}
