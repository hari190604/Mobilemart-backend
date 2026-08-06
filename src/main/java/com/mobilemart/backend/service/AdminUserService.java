package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.UserAdminResponse;
import com.mobilemart.backend.dto.UserAdminUpdateRequest;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserAdminResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToAdminResponse).collect(Collectors.toList());
    }

    public UserAdminResponse updateUserStatus(Long id, UserAdminUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setRole(request.getRole());
        user.setEnabled(request.getEnabled());
        
        userRepository.save(user);
        
        return mapToAdminResponse(user);
    }

    private UserAdminResponse mapToAdminResponse(User user) {
        return UserAdminResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
