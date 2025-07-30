package com.watches.backend.Dto.ProductDto;

public class ProductDto {

    private String name;
    private String description;
    private String image;
    private String brand;
    private String handsColor;
    private String backgroundColor;
    private String bandColor;
    private String numberingFormat;
    private String bandMaterial;
    private String caseMaterial;
    private String DisplayType;
    private String shape;
    private Boolean includesDate;
    private Boolean hasFullNumerals;
    private Boolean hasTickingSound;
    private Boolean waterProof;
    private Boolean changeableBand;
    private Double size;
    private Double weight;
    private Double price;
    private Integer quantity;

    public ProductDto(String name,
                      String description,
                      String image,
                      String brand,
                      Double size,
                      Double weight,
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
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
