package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUser_UserId(Long userId);
    Optional<CartItem> findByUser_UserIdAndProduct_ProductId(Long userId, Integer productId);
    
    @Transactional
    void deleteByUser_UserId(Long userId);
}
