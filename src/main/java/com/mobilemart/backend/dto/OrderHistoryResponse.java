package com.mobilemart.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderHistoryResponse {
    private String role;
    private OrderHistoryData orders;
    private String username;
}
