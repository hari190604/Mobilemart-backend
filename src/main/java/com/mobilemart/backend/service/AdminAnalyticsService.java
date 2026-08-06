package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.BusinessAnalyticsResponse;
import com.mobilemart.backend.entity.Order;
import com.mobilemart.backend.entity.OrderItem;
import com.mobilemart.backend.entity.OrderStatus;
import com.mobilemart.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminAnalyticsService {

    @Autowired
    private OrderRepository orderRepository;

    public BusinessAnalyticsResponse getDailyAnalytics() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<Order> orders = orderRepository.findByStatusAndDateRange(OrderStatus.SUCCESS, start, end);
        return mapToAnalytics("DAILY", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), orders);
    }

    public BusinessAnalyticsResponse getMonthlyAnalytics() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);
        List<Order> orders = orderRepository.findByStatusAndDateRange(OrderStatus.SUCCESS, start, end);
        return mapToAnalytics("MONTHLY", LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy")), orders);
    }

    public BusinessAnalyticsResponse getYearlyAnalytics() {
        LocalDateTime start = LocalDate.now().withDayOfYear(1).atStartOfDay();
        LocalDateTime end = start.plusYears(1);
        List<Order> orders = orderRepository.findByStatusAndDateRange(OrderStatus.SUCCESS, start, end);
        return mapToAnalytics("YEARLY", String.valueOf(LocalDate.now().getYear()), orders);
    }

    public BusinessAnalyticsResponse getOverallAnalytics() {
        List<Order> orders = orderRepository.findAllByStatus(OrderStatus.SUCCESS);
        return mapToAnalytics("OVERALL", "Lifetime", orders);
    }

    private BusinessAnalyticsResponse mapToAnalytics(String period, String label, List<Order> orders) {
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orders.size();

        long totalUnitsSold = orders.stream()
                .flatMap(o -> o.getOrderItems().stream())
                .mapToInt(OrderItem::getQuantity)
                .sum();

        List<BusinessAnalyticsResponse.OrderSummaryDto> orderSummaries = orders.stream().map(o -> {
            List<BusinessAnalyticsResponse.OrderLineItemDto> items = o.getOrderItems().stream().map(item -> 
                BusinessAnalyticsResponse.OrderLineItemDto.builder()
                        .productName(item.getProduct().getName())
                        .brand(item.getProduct().getCategory().getCategoryName()) // Fallback to category as brand is not heavily typed
                        .category(item.getProduct().getCategory().getCategoryName())
                        .imageUrl(item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty() ? item.getProduct().getImages().get(0).getImageUrl() : "")
                        .quantity(item.getQuantity())
                        .unitPrice(item.getPricePerUnit())
                        .totalLinePrice(item.getTotalPrice())
                        .build()
            ).collect(Collectors.toList());

            return BusinessAnalyticsResponse.OrderSummaryDto.builder()
                    .orderId(o.getOrderId())
                    .customerName(o.getUser().getFullName())
                    .customerEmail(o.getUser().getEmail())
                    .totalAmount(o.getTotalAmount())
                    .purchaseDate(o.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMM yyyy • hh:mm a")))
                    .items(items)
                    .build();
        }).collect(Collectors.toList());

        return BusinessAnalyticsResponse.builder()
                .period(period)
                .label(label)
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .totalUnitsSold(totalUnitsSold)
                .orders(orderSummaries)
                .build();
    }
}
