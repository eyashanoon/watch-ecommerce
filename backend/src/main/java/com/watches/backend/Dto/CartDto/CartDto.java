package com.watches.backend.Dto.CartDto;

import com.watches.backend.model.Customer;
import com.watches.backend.model.ProductItem;
import java.util.List;

public class CartDto {
    private Customer customer;
    private List<ProductItem> items;

    public CartDto(Customer customer, List<ProductItem> items) {
        this.customer = customer;
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomerName(Customer customer) {
        this.customer = customer;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
}
