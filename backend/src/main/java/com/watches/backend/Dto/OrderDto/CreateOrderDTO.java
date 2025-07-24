package com.watches.backend.Dto.OrderDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateOrderDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Size(min = 1, message = "At least one item is required")
    private List<CreateOrderItemDTO> items;

    public CreateOrderDTO() {}

    public CreateOrderDTO(Long customerId, List<CreateOrderItemDTO> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CreateOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemDTO> items) {
        this.items = items;
    }
}
