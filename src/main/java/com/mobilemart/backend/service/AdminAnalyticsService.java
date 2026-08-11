package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.BusinessAnalyticsResponse;
import com.mobilemart.backend.dto.BusinessAnalyticsResponse.ChartDataPointDto;
import com.mobilemart.backend.entity.Order;
import com.mobilemart.backend.entity.OrderItem;
import com.mobilemart.backend.entity.OrderStatus;
import com.mobilemart.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminAnalyticsService {

    @Autowired
    private OrderRepository orderRepository;

    private static class Aggregator {
        BigDecimal revenue = BigDecimal.ZERO;
        long orders = 0;
        long units = 0;

        void addOrder(Order o) {
            revenue = revenue.add(o.getTotalAmount());
            orders++;
            units += o.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();
        }
    }

    private List<OrderStatus> getValidStatuses() {
        return Arrays.asList(OrderStatus.SUCCESS, OrderStatus.SHIPPED, OrderStatus.OUT_FOR_DELIVERY, OrderStatus.DELIVERED);
    }

    public BusinessAnalyticsResponse getAnalyticsForLastNDays(int days) {
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime start = end.minusDays(days);
        List<Order> orders = orderRepository.findByStatusInAndDateRange(getValidStatuses(), start, end);
        
        Map<LocalDate, Aggregator> dailySums = new TreeMap<>();
        for (int i = days - 1; i >= 0; i--) {
            dailySums.put(LocalDate.now().minusDays(i), new Aggregator());
        }
        
        for (Order o : orders) {
            LocalDate d = o.getCreatedAt().toLocalDate();
            if (dailySums.containsKey(d)) {
                dailySums.get(d).addOrder(o);
            }
        }
        
        List<ChartDataPointDto> graphData = new ArrayList<>();
        dailySums.forEach((date, agg) -> graphData.add(ChartDataPointDto.builder()
                .label(date.format(DateTimeFormatter.ofPattern("MMM dd")))
                .revenue(agg.revenue)
                .ordersCount(agg.orders)
                .unitsSold(agg.units)
                .build()));

        return mapToAnalytics(days + " DAYS", "Last " + days + " Days", orders, graphData);
    }

    public BusinessAnalyticsResponse get1YearAnalytics() {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime end = currentMonth.plusMonths(1).atStartOfDay();
        LocalDateTime start = end.minusMonths(12);
        List<Order> orders = orderRepository.findByStatusInAndDateRange(getValidStatuses(), start, end);
        
        Map<LocalDate, Aggregator> monthlySums = new TreeMap<>();
        for (int i = 11; i >= 0; i--) {
            monthlySums.put(currentMonth.minusMonths(i), new Aggregator());
        }
        
        for (Order o : orders) {
            LocalDate d = o.getCreatedAt().toLocalDate().withDayOfMonth(1);
            if (monthlySums.containsKey(d)) {
                monthlySums.get(d).addOrder(o);
            }
        }
        
        List<ChartDataPointDto> graphData = new ArrayList<>();
        monthlySums.forEach((date, agg) -> graphData.add(ChartDataPointDto.builder()
                .label(date.format(DateTimeFormatter.ofPattern("MMM yyyy")))
                .revenue(agg.revenue)
                .ordersCount(agg.orders)
                .unitsSold(agg.units)
                .build()));

        return mapToAnalytics("1 YEAR", "Last 12 Months", orders, graphData);
    }

    public BusinessAnalyticsResponse getLifetimeAnalytics() {
        List<Order> orders = orderRepository.findAllByStatusIn(getValidStatuses());
        
        Map<Integer, Aggregator> yearlySums = new TreeMap<>();
        
        for (Order o : orders) {
            int y = o.getCreatedAt().getYear();
            yearlySums.putIfAbsent(y, new Aggregator());
            yearlySums.get(y).addOrder(o);
        }
        
        if (yearlySums.isEmpty()) {
            yearlySums.put(LocalDate.now().getYear(), new Aggregator());
        }
        
        List<ChartDataPointDto> graphData = new ArrayList<>();
        yearlySums.forEach((year, agg) -> graphData.add(ChartDataPointDto.builder()
                .label(String.valueOf(year))
                .revenue(agg.revenue)
                .ordersCount(agg.orders)
                .unitsSold(agg.units)
                .build()));

        return mapToAnalytics("LIFETIME", "Lifetime Overview", orders, graphData);
    }
    
    // Kept to fulfill AdminDashboard top stat widgets logic silently picking /admin/revenue/overall initially
    public BusinessAnalyticsResponse getOverallAnalytics() {
        return getLifetimeAnalytics();
    }

    private BusinessAnalyticsResponse mapToAnalytics(String period, String label, List<Order> orders, List<ChartDataPointDto> graphData) {
        Aggregator totalAgg = new Aggregator();
        for (Order o : orders) totalAgg.addOrder(o);

        List<BusinessAnalyticsResponse.OrderSummaryDto> orderSummaries = orders.stream().map(o -> {
            List<BusinessAnalyticsResponse.OrderLineItemDto> items = o.getOrderItems().stream().map(item -> 
                BusinessAnalyticsResponse.OrderLineItemDto.builder()
                        .productName(item.getProduct().getName())
                        .brand(item.getProduct().getCategory().getCategoryName())
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
                .totalRevenue(totalAgg.revenue)
                .totalOrders(totalAgg.orders)
                .totalUnitsSold(totalAgg.units)
                .orders(orderSummaries)
                .graphData(graphData)
                .build();
    }
}
