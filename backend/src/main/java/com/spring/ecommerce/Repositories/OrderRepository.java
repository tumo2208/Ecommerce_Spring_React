package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Additional query methods can be defined here if needed
}
