package com.watches.backend.Dto.ProductDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

public class WishlistProductDto {
    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Product image is required")
    private Long imageId;

    @NotBlank(message = "Product brand is required")
    private String brand;

    private String size;
    private Boolean waterProof;
    private Boolean crystal;
    private String color;

    private String type;

    private Boolean supportsDate;
    private String numberingFormat;
    private Boolean hasFullNumerals;
    private Boolean hasTickingSound;

    @NotBlank(message = "Product price is required")
    @PositiveOrZero(message = "Product price must be zero or positive")
    private Double price = 0.0;

    @PositiveOrZero(message = "Product quantity must be zero or positive")
    private Integer quantity = 0;

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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
}
