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
}
