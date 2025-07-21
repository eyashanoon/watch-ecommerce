package com.watches.backend.Dto.ProductDto;

import com.watches.backend.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateProductDto {

    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Product brand is required")
    private String brand;

    private String description;
    private String type;

    @NotBlank(message = "Product gender is required")
    private Gender gender = Gender.ALL;

    private String size;

    @NotBlank(message = "Product price is required")
    @PositiveOrZero(message = "Product price must be zero or positive")
    private Double price = 0.0;

    @PositiveOrZero(message = "Product quantity must be zero or positive")
    private Integer quantity = 0;
    @PositiveOrZero(message = "Product discount must be zero or positive")
    private Double discount = 0.0;

    public CreateProductDto(
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

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
