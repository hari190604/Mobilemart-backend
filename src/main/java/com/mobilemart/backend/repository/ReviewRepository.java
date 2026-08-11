package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductId(Integer productId);
    boolean existsByUser_UserIdAndProduct_ProductId(Long userId, Integer productId);
}
