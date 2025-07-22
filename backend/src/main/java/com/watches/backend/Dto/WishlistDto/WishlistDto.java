package com.watches.backend.Dto.WishlistDto;

import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;

import java.util.List;

public class WishlistDto {
    private Customer customer;
    private List<Product> products;

    public WishlistDto() {
    }

    public WishlistDto(Customer customer) {
        this.customer = customer;
    }

    public WishlistDto(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
