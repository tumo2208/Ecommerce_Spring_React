package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Additional query methods can be defined here if needed
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameOrDescriptionContainingIgnoreCase(String name, String description);

}
