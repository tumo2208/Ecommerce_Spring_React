package com.spring.ecommerce.Services.Implementations;

import com.spring.ecommerce.DTOs.CategoryDTO;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Exceptions.ResourceNotFoundException;
import com.spring.ecommerce.Mapping.EntityDTOMapping;
import com.spring.ecommerce.Models.Category;
import com.spring.ecommerce.Repositories.CategoryRepository;
import com.spring.ecommerce.Services.Interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private EntityDTOMapping entityDTOMapping;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Response<?> createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Category created successfully");
        return response;
    }

    @Override
    public Response<?> updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(name);
        categoryRepository.save(category);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Category updated successfully");
        return response;
    }

    @Override
    public Response<?> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = categories.stream().map(entityDTOMapping::categoryDtoFromCategory).toList();
        Response<List<CategoryDTO>> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Categories retrieved successfully");
        response.setData(categoryDTOS);
        return response;
    }

    @Override
    public Response<?> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        CategoryDTO categoryDTO = entityDTOMapping.categoryDtoFromCategory(category);
        Response<CategoryDTO> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Category retrieved successfully");
        response.setData(categoryDTO);
        return response;
    }

    @Override
    public Response<?> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Category deleted successfully");
        return response;
    }
}
