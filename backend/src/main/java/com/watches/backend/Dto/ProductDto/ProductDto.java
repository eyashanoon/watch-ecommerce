package com.watches.backend.Dto.ProductDto;

import com.watches.backend.model.Discount;

public class ProductDto {

    private String name;
    private String brand;
    private String description;
    private String image;
    private String size;
    private Boolean waterProof;
    private Boolean crystal;
    private String color;
    private String type;
    private Boolean supportsDate;
    private String numberingFormat;
    private Boolean hasFullNumerals;
    private Boolean hasTickingSound;
    private Double price;
    private Integer quantity;
    private Discount discount;

    public ProductDto(String name,
                      String brand,
                      String description,
                      String image,
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
