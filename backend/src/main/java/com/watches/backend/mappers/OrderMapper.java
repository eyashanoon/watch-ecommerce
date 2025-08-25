package com.watches.backend.mappers;

import com.watches.backend.Dto.order.*;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

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

    public static void updateStatus(Order order, OrderStatus status) {
        if (order != null && status != null) {
            order.setStatus(status);
        }
    }
}
