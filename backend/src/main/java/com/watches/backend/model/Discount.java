package com.watches.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discount")
    private Set<Product> products;

    @Positive
    private Double discount;

    private LocalDateTime EndDate;

    public Discount() {}

    public Discount(Set<Product> product, Double discount, LocalDateTime endDate) {
        this.products = product;
        this.discount = discount;
        EndDate = endDate;
    }

    public Set<Product> getProduct() {
        return products;
    }

    public void setProduct(Set<Product> product) {
        this.products = product;
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
