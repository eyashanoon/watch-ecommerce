package com.watches.backend.model;

import com.watches.backend.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

@Entity
public class Product {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String name;
    private String brand;
    private String description;
    private String type;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String size;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer quantity;
    private Double discount;

    public Product() {}

    public Product(
                   String name,
                   String brand,
                   String description,
                   String type,
                   Gender gender,
                   String size,
                   Double price,
                   Integer quantity,
                   Double discount
    ) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description) && Objects.equals(type, product.type) && gender == product.gender && Objects.equals(size, product.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, description, type, gender, size, price, quantity, discount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                '}';
    }
}
