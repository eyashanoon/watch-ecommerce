package com.watches.backend.Dto.OrderDto;

import com.watches.backend.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;

    public OrderDTO() {}

    public OrderDTO(Long orderId, Long customerId, String customerName,
                    List<OrderItemDTO> items, Double totalPrice,
                    OrderStatus status, LocalDateTime placedAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.placedAt = placedAt;
        this.updatedAt = updatedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPlacedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
