package com.spring.ecommerce.DTOs;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private int quantity;
}