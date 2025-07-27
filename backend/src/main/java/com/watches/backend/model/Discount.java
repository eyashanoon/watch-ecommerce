package com.watches.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Positive
    private Double discount;

    private LocalDateTime EndDate;

    public Discount() {}

    public Discount(Product product, Double discount, LocalDateTime endDate) {
        this.product = product;
        this.discount = discount;
        EndDate = endDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDateTime getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        EndDate = endDate;
    }
}
