package com.watches.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {
    private @Id
    @GeneratedValue
    String id;
    private String name;
    private String brand;
    private String description;
    private String type;
    private String gender;
    private String size;
    private Double price;
    private Integer quantity;
    private Double discount;

    public Product() {}

    public Product(String id,
                   String name,
                   String brand,
                   String description,
                   String type,
                   String gender,
                   String size,
                   Double price,
                   Integer quantity,
                   Double discount
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.type = type;
        this.gender = gender;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
