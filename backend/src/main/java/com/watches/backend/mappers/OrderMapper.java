package com.watches.backend.mappers;

import com.watches.backend.Dto.OrderDto.*;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    // ========== Entity to DTO ==========

    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;

        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getUsername(),
                toItemDTOList(order.getItems()),
                order.getTotalPrice(),
                order.getStatus(),
                order.getPlacedAt(),
                order.getUpdatedAt()
        );
    }

    public static OrderItemDTO toItemDTO(OrderItem item) {
        if (item == null) return null;

        return new OrderItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getPriceAtPurchase(),
                item.getQuantity()
        );
    }

    public static List<OrderItemDTO> toItemDTOList(List<OrderItem> items) {
        if (items == null) return List.of();

        return items.stream()
                .map(OrderMapper::toItemDTO)
                .collect(Collectors.toList());
    }

    // ========== DTO to Entity ==========

    // Create new Order from DTO + existing Customer + calculated totalPrice
    public static Order fromCreateDTO(CreateOrderDTO dto, Customer customer ) {
        if (dto == null || customer == null) return null;

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.REQUESTED);
        return order;
    }

    // Create new OrderItem from DTO + product + order
    public static OrderItem fromCreateItemDTO(CreateOrderItemDTO dto, Product product, Order order) {
        if (dto == null || product == null || order == null) return null;

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
         return item;
    }

    // Update Order status from DTO
    public static void updateStatus(Order order, UpdateOrderStatusDTO dto) {
        if (order != null && dto != null) {
            order.setStatus(dto.getStatus());
         }
    }

    public static  CreateOrderDTO fromCartToCreateOrderDTO(Cart cart) {
        if (cart == null) return null;
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCustomerId(cart.getCustomer().getId());
        List<CreateOrderItemDTO> items=cart.getItems().stream().map(OrderMapper::fromCartItemToCreateOrderItemDTO).toList();
        createOrderDTO.setItems(items);
        return createOrderDTO;

    }
    public static CreateOrderItemDTO fromCartItemToCreateOrderItemDTO( ProductItem cartItem) {
        if (cartItem == null) return null;
        CreateOrderItemDTO createOrderItemDTO = new CreateOrderItemDTO();
        createOrderItemDTO.setProductId(cartItem.getProduct().getId());
        createOrderItemDTO.setQuantity(cartItem.getQuantity());
        return createOrderItemDTO;


    }
}
