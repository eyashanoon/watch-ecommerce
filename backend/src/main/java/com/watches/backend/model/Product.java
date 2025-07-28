package com.watches.backend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.HashMap;
import java.util.Map;
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

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    private String size;
    private Boolean waterProof;
    private Boolean crystal;
    private String color;

    private String type;
    private Boolean supportsDate;
    private String numberingFormat;
    private Boolean hasFullNumerals;
    private Boolean hasTickingSound;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;


    public Product() {}

    public Product(
                   String name,
                   String brand,
                   String description,
                   Image image,
                   String size,
                   Boolean waterProof,
                   Boolean crystal,
                   String color,
                   String type,
                   Boolean supportsDate,
                   String numberingFormat,
                   Boolean hasFullNumerals,
                   Boolean hasTickingSound,
                   Double price,
                   Integer quantity,
                   Discount discount) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.size = size;
        this.waterProof = waterProof;
        this.crystal = crystal;
        this.color = color;
        this.type = type;
        this.supportsDate = supportsDate;
        this.numberingFormat = numberingFormat;
        this.hasFullNumerals = hasFullNumerals;
        this.hasTickingSound = hasTickingSound;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getWaterProof() {
        return waterProof;
    }

    public void setWaterProof(Boolean waterProof) {
        this.waterProof = waterProof;
    }

    public Boolean getCrystal() {
        return crystal;
    }

    public void setCrystal(Boolean crystal) {
        this.crystal = crystal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getSupportsDate() {
        return supportsDate;
    }

    public void setSupportsDate(Boolean supportsDate) {
        this.supportsDate = supportsDate;
    }

    public String getNumberingFormat() {
        return numberingFormat;
    }

    public void setNumberingFormat(String numberingFormat) {
        this.numberingFormat = numberingFormat;
    }

    public Boolean getHasFullNumerals() {
        return hasFullNumerals;
    }

    public void setHasFullNumerals(Boolean hasFullNumerals) {
        this.hasFullNumerals = hasFullNumerals;
    }

    public Boolean getHasTickingSound() {
        return hasTickingSound;
    }

    public void setHasTickingSound(Boolean hasTickingSound) {
        this.hasTickingSound = hasTickingSound;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description) && Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, description, type, price, quantity, discount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                '}';
    }
}
