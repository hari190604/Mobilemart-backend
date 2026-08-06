package com.mobilemart.backend.dto;

import com.mobilemart.backend.entity.Role;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class UserAdminUpdateRequest {
    
    @NotNull(message = "Role is required")
    private Role role;
    
    @NotNull(message = "Enabled status is required")
    private Boolean enabled;
}
