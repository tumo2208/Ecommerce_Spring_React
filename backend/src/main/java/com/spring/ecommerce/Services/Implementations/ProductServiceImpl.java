package com.spring.ecommerce.Services.Implementations;

import com.spring.ecommerce.DTOs.ProductDTO;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Exceptions.ResourceNotFoundException;
import com.spring.ecommerce.Mapping.EntityDTOMapping;
import com.spring.ecommerce.Models.Category;
import com.spring.ecommerce.Models.Product;
import com.spring.ecommerce.Repositories.CategoryRepository;
import com.spring.ecommerce.Repositories.ProductRepository;
import com.spring.ecommerce.Services.CloudinaryService;
import com.spring.ecommerce.Services.Interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private EntityDTOMapping entityDTOMapping;

    @Override
    public Response<?> createProduct(Long categoryId, String name, String description, BigDecimal price, MultipartFile image) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        String imageId = UUID.randomUUID().toString();
        String imageUrl = cloudinaryService.uploadImage(image, imageId);
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        productRepository.save(product);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Product created successfully");
        return response;
    }

    @Override
    public Response<?> updateProduct(Long productId, Long categoryId, String name, String description, BigDecimal price, MultipartFile image) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        Category category = null;
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            String imageId = UUID.randomUUID().toString();
            imageUrl = cloudinaryService.uploadImage(image, imageId);
        }
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        }

        if (name != null) product.setName(name);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setCategory(category);

        productRepository.save(product);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Product updated successfully");
        return response;
    }

    @Override
    public Response<?> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        productRepository.delete(product);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Product deleted successfully");
        return response;
    }

    @Override
    public Response<?> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(entityDTOMapping::productDtoFromProduct)
                .toList();
        Response<List<ProductDTO>> response = new Response<>();
        response.setStatus(200);
        response.setMessage("All products retrieved successfully");
        response.setData(productDTOs);
        return response;
    }

    @Override
    public Response<?> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        ProductDTO productDTO = entityDTOMapping.productDtoFromProduct(product);
        Response<ProductDTO> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Product retrieved successfully");
        response.setData(productDTO);
        return response;
    }

    @Override
    public Response<?> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this category");
        }
        List<ProductDTO> productDTOs = products.stream()
                .map(entityDTOMapping::productDtoFromProduct)
                .toList();
        Response<List<ProductDTO>> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Products retrieved successfully");
        response.setData(productDTOs);
        return response;
    }

    @Override
    public Response<?> searchProducts(String keyword) {
        List<Product> products = productRepository.findByNameOrDescriptionContainingIgnoreCase(keyword, keyword);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found matching the keyword: " + keyword);
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(entityDTOMapping::productDtoFromProduct)
                .toList();
        Response<List<ProductDTO>> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Products matching the keyword retrieved successfully");
        response.setData(productDTOs);
        return response;
    }
}
