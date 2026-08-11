package com.mobilemart.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private CategoryDto category;
    private List<ProductImageDto> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean featured;
    private Integer displayPriority;
}
