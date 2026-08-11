package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder_OrderId(String orderId);

    @Query("SELECT COUNT(oi) > 0 FROM OrderItem oi JOIN oi.order o WHERE o.user.userId = :userId AND oi.product.productId = :productId AND (o.status = 'SUCCESS' OR o.status = 'DELIVERED' OR o.status = 'SHIPPED')")
    boolean hasUserPurchasedProduct(@Param("userId") Long userId, @Param("productId") Integer productId);
}
