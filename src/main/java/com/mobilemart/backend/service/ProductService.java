package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.*;
import com.mobilemart.backend.entity.Category;
import com.mobilemart.backend.entity.Product;
import com.mobilemart.backend.entity.ProductImage;
import com.mobilemart.backend.repository.CategoryRepository;
import com.mobilemart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse createProduct(ProductRequest request) {
        Optional<Category> categoryOpt = categoryRepository.findById(request.getCategoryId());
        if (categoryOpt.isEmpty()) {
            return new ApiResponse(false, "Category not found");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(categoryOpt.get());

        // Handle Images
        List<ProductImage> images = new ArrayList<>();
        if (request.getImageUrls() != null) {
            for (String url : request.getImageUrls()) {
                ProductImage image = new ProductImage();
                image.setImageUrl(url);
                image.setProduct(product);
                images.add(image);
            }
        }
        product.setImages(images);

        Product savedProduct = productRepository.save(product);
        return new ApiResponse(true, "Product created successfully", mapToDto(savedProduct));
    }

    public ApiResponse updateProduct(Integer productId, ProductRequest request) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return new ApiResponse(false, "Product not found");
        }

        Optional<Category> categoryOpt = categoryRepository.findById(request.getCategoryId());
        if (categoryOpt.isEmpty()) {
            return new ApiResponse(false, "Category not found");
        }

        Product product = productOpt.get();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(categoryOpt.get());

        // Update Images (Clear old and add new)
        product.getImages().clear();
        if (request.getImageUrls() != null) {
            for (String url : request.getImageUrls()) {
                ProductImage image = new ProductImage();
                image.setImageUrl(url);
                image.setProduct(product);
                product.getImages().add(image);
            }
        }

        Product savedProduct = productRepository.save(product);
        return new ApiResponse(true, "Product updated successfully", mapToDto(savedProduct));
    }

    public ApiResponse deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            return new ApiResponse(false, "Product not found");
        }
        productRepository.deleteById(productId);
        return new ApiResponse(true, "Product deleted successfully");
    }

    public ApiResponse getProductById(Integer productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            return new ApiResponse(true, "Product fetched successfully", mapToDto(productOpt.get()));
        }
        return new ApiResponse(false, "Product not found");
    }

    public ApiResponse getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        
        Page<ProductResponse> responsePage = productPage.map(this::mapToDto);
        return new ApiResponse(true, "Products fetched successfully", responsePage);
    }

    public ApiResponse getProductsByCategory(Integer categoryId, int page, int size) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ApiResponse(false, "Category not found");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByCategory_CategoryId(categoryId, pageable);
        
        Page<ProductResponse> responsePage = productPage.map(this::mapToDto);
        return new ApiResponse(true, "Products fetched successfully", responsePage);
    }

    private ProductResponse mapToDto(Product product) {
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(product.getCategory().getCategoryId())
                .categoryName(product.getCategory().getCategoryName())
                .build();

        List<ProductImageDto> imageDtos = new ArrayList<>();
        if (product.getImages() != null) {
            imageDtos = product.getImages().stream()
                    .map(img -> ProductImageDto.builder()
                            .imageId(img.getImageId())
                            .imageUrl(img.getImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(categoryDto)
                .images(imageDtos)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
