package com.mobilemart.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BusinessAnalyticsResponse {
    private String period;
    private String label;
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalUnitsSold;
    private List<OrderSummaryDto> orders;
    private List<ChartDataPointDto> graphData;

    @Data
    @Builder
    public static class ChartDataPointDto {
        private String label;
        private BigDecimal revenue;
        private Long ordersCount;
        private Long unitsSold;
    }
    
    @Data
    @Builder
    public static class OrderSummaryDto {
        private String orderId;
        private String customerName;
        private String customerEmail;
        private BigDecimal totalAmount;
        private String purchaseDate;
        private List<OrderLineItemDto> items;
    }
    
    @Data
    @Builder
    public static class OrderLineItemDto {
        private String productName;
        private String brand;
        private String category;
        private String imageUrl;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalLinePrice;
    }
}
