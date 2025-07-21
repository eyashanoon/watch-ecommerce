package com.watches.backend.Dto.WishlistDto;

import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreateWishlistDto {
    @NotBlank(message = "wishlist Owner is required")
    private Customer customer;

    private List<Product> products;

    public CreateWishlistDto() {
    }

    public CreateWishlistDto(Customer customer) {
        this.customer = customer;
    }

    public CreateWishlistDto(Customer customer, List<Product> products) {
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
