package com.watches.backend.Dto.CartDto;

import com.watches.backend.model.Customer;
import com.watches.backend.model.ProductItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreateCartDto {
    @NotBlank(message = "Cart Customer is required")
    private Customer customer;

    private List<ProductItem> Items;

    public CreateCartDto(Customer customer, List<ProductItem> items) {
        this.customer = customer;
        Items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductItem> getItems() {
        return Items;
    }

    public void setItems(List<ProductItem> items) {
        Items = items;
    }
}
