package com.spring.ecommerce.Services.Interfaces;

import com.spring.ecommerce.DTOs.OrderRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {
    Response<?> placeOrder(OrderRequest orderRequest);
    Response<?> updateOrderItemStatus(Long orderItemId, String status);
    Response<?> filterOrderItems(OrderStatus status, LocalDate startDate, LocalDate endDate,
                                 Long itemId, Pageable pageable);
}
