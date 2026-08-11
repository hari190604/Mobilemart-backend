package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.ReviewRequest;
import com.mobilemart.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse> addReview(Authentication authentication, @RequestBody ReviewRequest request) {
        String username = authentication.getName();
        ApiResponse response = reviewService.addReview(username, request);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }
}
