package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {

    // Additional query methods can be defined here if needed
    @EntityGraph(attributePaths = {"product"})
    Page<OrderItem> findAll(Specification<OrderItem> spec, Pageable pageable);
}
