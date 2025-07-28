package com.watches.backend.Dto.OrderDto;

import com.watches.backend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusDTO {

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    public UpdateOrderStatusDTO() {}

    public UpdateOrderStatusDTO(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
