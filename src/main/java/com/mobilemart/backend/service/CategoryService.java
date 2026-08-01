package com.mobilemart.backend.service;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.dto.CategoryDto;
import com.mobilemart.backend.entity.Category;
import com.mobilemart.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse createCategory(CategoryDto request) {
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            return new ApiResponse(false, "Category already exists");
        }
        
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        
        Category savedCategory = categoryRepository.save(category);
        
        return new ApiResponse(true, "Category created successfully", mapToDto(savedCategory));
    }

    public ApiResponse getAllCategories() {
        List<CategoryDto> categories = categoryRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ApiResponse(true, "Categories fetched successfully", categories);
    }
    
    public ApiResponse updateCategory(Integer id, CategoryDto request) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return new ApiResponse(false, "Category not found");
        }
        
        Category category = categoryOpt.get();
        // Check if new name exists and it's not the same category
        if (!category.getCategoryName().equals(request.getCategoryName()) && 
            categoryRepository.existsByCategoryName(request.getCategoryName())) {
            return new ApiResponse(false, "Category name already exists");
        }
        
        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);
        
        return new ApiResponse(true, "Category updated successfully", mapToDto(category));
    }
    
    public ApiResponse deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            return new ApiResponse(false, "Category not found");
        }
        categoryRepository.deleteById(id);
        return new ApiResponse(true, "Category deleted successfully");
    }

    private CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
