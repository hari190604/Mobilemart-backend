package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByUser_UserId(Long userId, Pageable pageable);
    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);

    @org.springframework.data.jpa.repository.Query("SELECT o FROM Order o WHERE o.status = :status AND o.createdAt >= :startDate AND o.createdAt < :endDate ORDER BY o.createdAt DESC")
    java.util.List<Order> findByStatusAndDateRange(
            @org.springframework.data.repository.query.Param("status") com.mobilemart.backend.entity.OrderStatus status, 
            @org.springframework.data.repository.query.Param("startDate") java.time.LocalDateTime startDate, 
            @org.springframework.data.repository.query.Param("endDate") java.time.LocalDateTime endDate
    );

    @org.springframework.data.jpa.repository.Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.createdAt DESC")
    java.util.List<Order> findAllByStatus(@org.springframework.data.repository.query.Param("status") com.mobilemart.backend.entity.OrderStatus status);
}
