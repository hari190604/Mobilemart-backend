package com.mobilemart.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderProductDTO {
    
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("product_id")
    private Integer productId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("price_per_unit")
    private BigDecimal pricePerUnit;
    
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("category")
    private String category;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("order_date")
    private java.time.LocalDateTime orderDate;
}
