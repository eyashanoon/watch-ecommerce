package com.watches.backend.Dto.ProductDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

public class CreateProductDto {

    @NotBlank(message = "Product name is required")
    @NotNull
    private String name;
    private String description;
    private MultipartFile image;

    @NotBlank(message = "Product brand is required")
    @NotNull
    private String brand;
    @NotBlank(message = "Product size is required")
    @NotNull
    private String size;
    @NotBlank(message = "Product weight is required")
    @NotNull
    private String weight;
    @NotBlank(message = "Product hands color is required")
    @NotNull
    private String handsColor;
    @NotBlank(message = "Product background color is required")
    @NotNull
    private String backgroundColor;
    @NotBlank(message = "Product band color is required")
    @NotNull
    private String bandColor;
    @NotBlank(message = "Product numbering format is required")
    @NotNull
    private String numberingFormat;
    @NotBlank(message = "Product band material is required")
    @NotNull
    private String bandMaterial;
    @NotBlank(message = "Product case material is required")
    @NotNull
    private String caseMaterial;
    @NotBlank(message = "Product Display type is required")
    @NotNull
    private String DisplayType;
    @NotBlank(message = "Product shape is required")
    @NotNull
    private String shape;

    @NotNull
    private Boolean includesDate;
    @NotNull
    private Boolean hasFullNumerals;
    @NotNull
    private Boolean hasTickingSound;
    @NotNull
    private Boolean waterProof;
    @NotNull
    private Boolean changeableBand;

    @PositiveOrZero(message = "Product price must be zero or positive")
    private Double price;

    @PositiveOrZero(message = "Product quantity must be zero or positive")
    private Integer quantity;

    public CreateProductDto(String name,
                            String description,
                            MultipartFile image,
                            String brand,
                            String size,
                            String weight,
                            String handsColor,
                            String backgroundColor,
                            String bandColor,
                            String numberingFormat,
                            String bandMaterial,
                            String caseMaterial,
                            String displayType,
                            String shape,
                            Boolean includesDate,
                            Boolean hasFullNumerals,
                            Boolean hasTickingSound,
                            Boolean waterProof,
                            Boolean changeableBand,
                            Double price,
                            Integer quantity) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.brand = brand;
        this.size = size;
        this.weight = weight;
        this.handsColor = handsColor;
        this.backgroundColor = backgroundColor;
        this.bandColor = bandColor;
        this.numberingFormat = numberingFormat;
        this.bandMaterial = bandMaterial;
        this.caseMaterial = caseMaterial;
        DisplayType = displayType;
        this.shape = shape;
        this.includesDate = includesDate;
        this.hasFullNumerals = hasFullNumerals;
        this.hasTickingSound = hasTickingSound;
        this.waterProof = waterProof;
        this.changeableBand = changeableBand;
        this.price = price;
        this.quantity = quantity;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHandsColor() {
        return handsColor;
    }

    public void setHandsColor(String handsColor) {
        this.handsColor = handsColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBandColor() {
        return bandColor;
    }

    public void setBandColor(String bandColor) {
        this.bandColor = bandColor;
    }

    public String getNumberingFormat() {
        return numberingFormat;
    }

    public void setNumberingFormat(String numberingFormat) {
        this.numberingFormat = numberingFormat;
    }

    public String getBandMaterial() {
        return bandMaterial;
    }

    public void setBandMaterial(String bandMaterial) {
        this.bandMaterial = bandMaterial;
    }

    public String getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(String caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String displayType) {
        DisplayType = displayType;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Boolean getIncludesDate() {
        return includesDate;
    }

    public void setIncludesDate(Boolean includesDate) {
        this.includesDate = includesDate;
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

    public Boolean getChangeableBand() {
        return changeableBand;
    }

    public void setChangeableBand(Boolean changeableBand) {
        this.changeableBand = changeableBand;
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
