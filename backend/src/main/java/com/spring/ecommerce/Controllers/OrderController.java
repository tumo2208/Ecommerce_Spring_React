package com.spring.ecommerce.Controllers;

import com.spring.ecommerce.DTOs.OrderRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Enums.OrderStatus;
import com.spring.ecommerce.Services.Interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Response response = orderService.placeOrder(orderRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to place order: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/update-status/{orderItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateOrderStatus(@RequestParam String status, @PathVariable Long orderItemId) {
        try {
            Response response = orderService.updateOrderItemStatus(orderItemId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to update order status: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> filterOrderItems(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            @RequestParam Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("product.name").ascending());
            OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
            Response response = orderService.filterOrderItems(orderStatus, start, end, itemId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to filter order items: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
