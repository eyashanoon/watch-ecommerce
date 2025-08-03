package com.watches.backend.Dto.ProductDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
