package com.watches.backend.model;

import jakarta.persistence.*;

@Embeddable
public class ProductItem {

    @ManyToOne
    @JoinColumn(name = "id")
    private Product product;

    private Integer quantity;
    private Double price;

    public ProductItem() {
        this.quantity = 0;
        this.price = 0.0;
    }

    public ProductItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = this.product.getPrice() * quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
