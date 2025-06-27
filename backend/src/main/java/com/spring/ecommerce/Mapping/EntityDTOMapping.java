package com.spring.ecommerce.Mapping;

import com.spring.ecommerce.DTOs.AddressDTO;
import com.spring.ecommerce.DTOs.CategoryDTO;
import com.spring.ecommerce.DTOs.OrderDTO;
import com.spring.ecommerce.DTOs.OrderItemDTO;
import com.spring.ecommerce.DTOs.ProductDTO;
import com.spring.ecommerce.DTOs.UserDTO;
import com.spring.ecommerce.Enums.UserRole;
import com.spring.ecommerce.Models.Address;
import com.spring.ecommerce.Models.Category;
import com.spring.ecommerce.Models.Order;
import com.spring.ecommerce.Models.OrderItem;
import com.spring.ecommerce.Models.Product;
import com.spring.ecommerce.Models.User;
import org.springframework.stereotype.Component;

@Component
public class EntityDTOMapping {

    public AddressDTO addressDtoFromAddress(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setCity(address.getCity());
        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setUserId(address.getUser() != null ? address.getUser().getId() : null);

        return addressDTO;
    }

    public CategoryDTO categoryDtoFromCategory(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }

    public OrderDTO orderDtoFromOrder(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderItemIdList(order.getOrderItemList() != null ?
            order.getOrderItemList().stream().map(OrderItem::getId).toList() : null);

        return orderDTO;
    }

    public OrderItemDTO orderItemDtoFromOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setStatus(orderItem.getStatus());
        orderItemDTO.setCreatedAt(orderItem.getCreatedAt());
        orderItemDTO.setProductId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null);
        orderItemDTO.setUserId(orderItem.getUser() != null ? orderItem.getUser().getId() : null);
        orderItemDTO.setOrderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null);

        return orderItemDTO;
    }

    public ProductDTO productDtoFromProduct(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        return productDTO;
    }

    public UserDTO userDtoFromUser(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole().name());
        userDTO.setOrderItemIdList(user.getOrderItemList() != null ?
            user.getOrderItemList().stream().map(OrderItem::getId).toList() : null);
        userDTO.setAddressId(user.getAddress() != null ? user.getAddress().getId() : null);
        userDTO.setCreatedAt(user.getCreatedAt());

        return userDTO;
    }
}
