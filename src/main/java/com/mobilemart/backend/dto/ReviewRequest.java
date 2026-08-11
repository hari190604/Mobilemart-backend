package com.mobilemart.backend.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer productId;
    private Integer rating;
    private String comment;
}
