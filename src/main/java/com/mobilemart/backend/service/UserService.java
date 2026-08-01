package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.UserProfileResponse;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        UserProfileResponse response = UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .mobileNumber(user.getMobileNumber())
                .email(user.getEmail())
                .build();

        return new ApiResponse(true, "User profile fetched successfully", response);
    }
}
