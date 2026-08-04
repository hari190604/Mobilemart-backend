package com.mobilemart.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderHistoryData {
    private List<OrderProductDTO> products;
}
