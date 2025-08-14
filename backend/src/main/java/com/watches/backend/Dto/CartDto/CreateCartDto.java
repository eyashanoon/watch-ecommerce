package com.watches.backend.Dto.CartDto;

import com.watches.backend.model.Customer;
import com.watches.backend.model.ProductItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateCartDto {
    @NotNull(message = "Cart Customer is required")
    private Long customerID;

    private List<ProductItem> Items;

    public CreateCartDto(Long customerID, List<ProductItem> items) {
        this.customerID = customerID;
        Items = items;
    }

    public Long getCustomer() {
        return customerID;
    }

    public void setCustomer(Long customer) {
        this.customerID = customer;
    }

    public List<ProductItem> getItems() {
        return Items;
    }

    public void setItems(List<ProductItem> items) {
        Items = items;
    }
}
