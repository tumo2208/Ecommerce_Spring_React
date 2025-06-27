package com.spring.ecommerce.Services.Implementations;

import com.spring.ecommerce.DTOs.OrderItemDTO;
import com.spring.ecommerce.DTOs.OrderRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Enums.OrderStatus;
import com.spring.ecommerce.Exceptions.ResourceNotFoundException;
import com.spring.ecommerce.Mapping.EntityDTOMapping;
import com.spring.ecommerce.Models.Order;
import com.spring.ecommerce.Models.OrderItem;
import com.spring.ecommerce.Models.Product;
import com.spring.ecommerce.Models.User;
import com.spring.ecommerce.Repositories.OrderItemRepository;
import com.spring.ecommerce.Repositories.OrderRepository;
import com.spring.ecommerce.Repositories.ProductRepository;
import com.spring.ecommerce.Services.Interfaces.OrderService;
import com.spring.ecommerce.Services.Interfaces.UserService;
import com.spring.ecommerce.Specification.OrderItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityDTOMapping entityDTOMapping;

    @Override
    public Response<?> placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Product not found with id: " + itemRequest.getProductId()));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setUser(user);
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
                    orderItem.setStatus(OrderStatus.PENDING);
                    return orderItem;
                }).collect(Collectors.toList());

        BigDecimal totalPrice = (orderRequest.getTotalPrice() != null &&
                orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0)
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setOrderItemList(orderItems);

        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        });

        orderRepository.save(order);

        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Order placed successfully");
        return response;
    }

    @Override
    public Response<?> updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + orderItemId));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepository.save(orderItem);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Order item status updated successfully");
        return response;
    }

    @Override
    public Response<?> filterOrderItems(OrderStatus status, LocalDate startDate, LocalDate endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = OrderItemSpecification.hasStatus(status)
                .and(OrderItemSpecification.createdBetween(startDate, endDate))
                .and(OrderItemSpecification.hasItemId(itemId));
        Page<OrderItem> orderItemsPage = orderItemRepository.findAll(spec, pageable);

        if (orderItemsPage.isEmpty()) {
            throw new ResourceNotFoundException("No order items found with the given criteria");
        }

        List<OrderItemDTO> orderItemDTOs = orderItemsPage.getContent().stream()
                .map(entityDTOMapping::orderItemDtoFromOrderItem)
                .collect(Collectors.toList());
        Response<List<OrderItemDTO>> response = new Response<>();
        response.setStatus(200);
        response.setMessage("Order items filtered successfully");
        response.setData(orderItemDTOs);
        response.setTotalPages(orderItemsPage.getTotalPages());
        response.setTotalElements(orderItemsPage.getTotalElements());
        return response;
    }
}
