package com.spring.ecommerce.Specification;

import com.spring.ecommerce.Models.OrderItem;
import org.springframework.data.jpa.domain.Specification;

import java.security.SecurityPermission;
import java.time.LocalDate;

public class OrderItemSpecification {

    public static Specification<OrderItem> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<OrderItem> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, criteriaBuilder) -> {
            if (start != null || end != null) {
                return criteriaBuilder.between(root.get("createdAt"), start, end);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), start);
            } else if (end != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
