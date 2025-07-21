package com.watches.backend.Dto;

import com.watches.backend.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDto {

    private String name;
    private String brand;
    private String description;
    private String type;
    private Gender gender;
    private String size;
    private Double price;
    private Integer quantity;
    private Double discount;

    public ProductDto(
            String name,
            String brand,
            String description,
            String type,
            Gender gender,
            String size,
            Double price,
            Integer quantity,
            Double discount) {
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

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Gender getGender() {
        return gender;
    }

    public String getSize() {
        return size;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getDiscount() {
        return discount;
    }
}
