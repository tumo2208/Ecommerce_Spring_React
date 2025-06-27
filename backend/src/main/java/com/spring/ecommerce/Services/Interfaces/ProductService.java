package com.spring.ecommerce.Services.Interfaces;

import com.spring.ecommerce.DTOs.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {
    Response<?> createProduct(Long categoryId, String name, String description, BigDecimal price, MultipartFile image);
    Response<?> updateProduct(Long productId, Long categoryId, String name,
                              String description, BigDecimal price, MultipartFile image);
    Response<?> deleteProduct(Long productId);
    Response<?> getAllProducts();
    Response<?> getProductById(Long productId);
    Response<?> getProductsByCategory(Long categoryId);
    Response<?> searchProducts(String keyword);
}
