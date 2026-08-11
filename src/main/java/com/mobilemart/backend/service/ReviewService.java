package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.ReviewRequest;
import com.mobilemart.backend.entity.Product;
import com.mobilemart.backend.entity.Review;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.OrderItemRepository;
import com.mobilemart.backend.repository.ProductRepository;
import com.mobilemart.backend.repository.ReviewRepository;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public ApiResponse addReview(String username, ReviewRequest request) {
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            return new ApiResponse(false, "Rating must be between 1 and 5");
        }

        User user = userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return new ApiResponse(false, "User not found");
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return new ApiResponse(false, "Product not found");
        }

        // Check if user has already reviewed the product
        if (reviewRepository.existsByUser_UserIdAndProduct_ProductId(user.getUserId(), product.getProductId())) {
            return new ApiResponse(false, "You have already reviewed this product");
        }

        // Check if user has actually purchased the product
        boolean hasPurchased = orderItemRepository.hasUserPurchasedProduct(user.getUserId(), product.getProductId());
        
        if (!hasPurchased) {
            return new ApiResponse(false, "You can only review products that you have successfully purchased");
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        
        reviewRepository.save(review);

        return new ApiResponse(true, "Review submitted successfully", null);
    }
}
