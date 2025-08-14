package com.watches.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ProductItem {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PositiveOrZero
    private Integer quantity;

    private Double price;
    private boolean deleted;

    public ProductItem() {
        this.quantity = 0;
        this.price = 0.0;
    }

    public ProductItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = this.product.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem that = (ProductItem) o;
        return Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getQuantity(), getPrice());
    }
}
