package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Additional query methods can be defined here if needed
}
