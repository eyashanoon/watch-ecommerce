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

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    private Boolean waterProof;
    private Boolean hasTickingSound;
    private Boolean includesDate;
    private Boolean hasFullNumerals;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "shape_id", referencedColumnName = "id")
    private Shape shape;

    @ManyToOne
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    private Band band;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "weight_id", referencedColumnName = "id")
    private Weight weight;

    @ManyToOne
    @JoinColumn(name = "casee_id", referencedColumnName = "id")
    private Case casee;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer quantity;

    public Case getCasee() {
        return casee;
    }

    public void setCasee(Case casee) {
        this.casee = casee;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Product() {}

    public Product(Long id,
                   String name,
                   String description,
                   Image image,
                   Boolean waterProof,
                   Boolean hasTickingSound,
                   Boolean includesDate,
                   Boolean hasFullNumerals,
                   Size size,
                   Brand brand,
                   Color color,
                   Shape shape,
                   Band band,
                   Discount discount,
                   Weight weight,
                   Double price,
                   Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.waterProof = waterProof;
        this.hasTickingSound = hasTickingSound;
        this.includesDate = includesDate;
        this.hasFullNumerals = hasFullNumerals;
        this.size = size;
        this.brand = brand;
        this.color = color;
        this.shape = shape;
        this.band = band;
        this.discount = discount;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getWaterProof() {
        return waterProof;
    }

    public void setWaterProof(Boolean waterProof) {
        this.waterProof = waterProof;
    }

    public Boolean getIncludesDate() {
        return includesDate;
    }

    public void setIncludesDate(Boolean includesDate) {
        this.includesDate = includesDate;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, waterProof, hasTickingSound, includesDate, hasFullNumerals, size, brand, color, shape, band, discount, weight, price, quantity);
    }


}
