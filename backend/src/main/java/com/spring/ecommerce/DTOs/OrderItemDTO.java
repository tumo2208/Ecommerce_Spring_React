package com.spring.ecommerce.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.Enums.OrderStatus;
import com.spring.ecommerce.Models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;

    private int quantity;
    private BigDecimal price;
    private OrderStatus status;

    private Long userId;

    private Long orderId;

    private Long productId;
    private LocalDate createdAt;
}
