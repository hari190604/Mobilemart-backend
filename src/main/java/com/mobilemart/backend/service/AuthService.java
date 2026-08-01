package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.*;
import com.mobilemart.backend.entity.JwtToken;
import com.mobilemart.backend.entity.Role;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.JwtTokenRepository;
import com.mobilemart.backend.repository.UserRepository;
import com.mobilemart.backend.security.CustomUserDetails;
import com.mobilemart.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Temporary storage for OTPs and Reset Tokens
    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> resetTokenStorage = new ConcurrentHashMap<>();

    public ApiResponse registerUser(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new ApiResponse(false, "Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse(false, "Email is already in use");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return new ApiResponse(false, "Username is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setMobileNumber(request.getMobileNumber());
        user.setEmail(request.getEmail());
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully");
    }

    public ApiResponse loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String jwt = jwtUtil.generateToken(userDetails);

        // Remove previous tokens for this user
        jwtTokenRepository.deleteByUserId(user.getUserId());

        // Create new token record
        JwtToken tokenRecord = new JwtToken();
        tokenRecord.setUserId(user.getUserId());
        tokenRecord.setToken(jwt);
        // expiration is set to 1 hour (same as token)
        tokenRecord.setExpiresAt(LocalDateTime.now().plusHours(1)); 
        jwtTokenRepository.save(tokenRecord);

        AuthResponse authResponse = AuthResponse.builder()
                .token(jwt)
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return new ApiResponse(true, "Login successful", authResponse);
    }

    public ApiResponse logoutUser(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        Optional<JwtToken> tokenOpt = jwtTokenRepository.findByToken(token);
        if (tokenOpt.isPresent()) {
            jwtTokenRepository.delete(tokenOpt.get());
            return new ApiResponse(true, "Logged out successfully");
        }
        
        return new ApiResponse(false, "Invalid or expired session");
    }

    public ApiResponse generateOtp(ForgotPasswordRequest request) {
        String identifier = request.getIdentifier();
        Optional<User> userOpt = userRepository.findByEmail(identifier);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(identifier);
        }

        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "User not found");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(identifier, otp);
        
        // Mocking OTP send
        System.out.println("=========================================");
        System.out.println("MOCK OTP FOR " + identifier + ": " + otp);
        System.out.println("=========================================");

        return new ApiResponse(true, "OTP generated and sent successfully");
    }

    public ApiResponse verifyOtp(VerifyOtpRequest request) {
        String identifier = request.getIdentifier();
        String storedOtp = otpStorage.get(identifier);

        if (storedOtp != null && storedOtp.equals(request.getOtp())) {
            otpStorage.remove(identifier);
            String resetToken = UUID.randomUUID().toString();
            resetTokenStorage.put(identifier, resetToken);
            return new ApiResponse(true, "OTP verified successfully", resetToken);
        }
        
        return new ApiResponse(false, "Invalid or expired OTP");
    }

    public ApiResponse resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return new ApiResponse(false, "Passwords do not match");
        }

        String identifier = request.getIdentifier();
        String storedToken = resetTokenStorage.get(identifier);

        if (storedToken == null || !storedToken.equals(request.getOtpToken())) {
            return new ApiResponse(false, "Invalid reset token");
        }

        Optional<User> userOpt = userRepository.findByEmail(identifier);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(identifier);
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            resetTokenStorage.remove(identifier);

            // Invalidate all tokens for this user
            jwtTokenRepository.deleteByUserId(user.getUserId());

            return new ApiResponse(true, "Password reset successfully");
        }

        return new ApiResponse(false, "User not found");
    }

    public ApiResponse changePassword(String username, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return new ApiResponse(false, "New passwords do not match");
        }

        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(username);
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                return new ApiResponse(false, "Incorrect current password");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            // Invalidate all tokens for this user to force re-login
            jwtTokenRepository.deleteByUserId(user.getUserId());

            return new ApiResponse(true, "Password changed successfully. Please log in again.");
        }

        return new ApiResponse(false, "User not found");
    }
}
