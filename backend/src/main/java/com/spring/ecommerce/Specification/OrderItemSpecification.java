package com.spring.ecommerce.Specification;

import com.spring.ecommerce.Enums.OrderStatus;
import com.spring.ecommerce.Models.OrderItem;
import org.springframework.data.jpa.domain.Specification;

import java.security.SecurityPermission;
import java.time.LocalDate;

public class OrderItemSpecification {

    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status) : null);
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

    public static Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null);
    }
}
