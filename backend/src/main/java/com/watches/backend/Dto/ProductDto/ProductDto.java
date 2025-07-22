package com.watches.backend.Dto.ProductDto;

import com.watches.backend.enums.Gender;

public class ProductDto {

    private String name;
    private String brand;
    private String description;
    private String type;
    private Double price;
    private Integer quantity;
    private Double discount;

    public ProductDto(
            String name,
            String brand,
            String description,
            String type,
            Double price,
            Integer quantity,
            Double discount) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.type = type;
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
