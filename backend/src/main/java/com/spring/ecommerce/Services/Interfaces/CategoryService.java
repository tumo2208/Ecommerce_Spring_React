package com.spring.ecommerce.Services.Interfaces;

import com.spring.ecommerce.DTOs.Response;

public interface CategoryService {
    Response<?> createCategory(String name);
    Response<?> updateCategory(Long id, String name);
    Response<?> getAllCategories();
    Response<?> getCategoryById(Long id);
    Response<?> deleteCategory(Long id);
}
