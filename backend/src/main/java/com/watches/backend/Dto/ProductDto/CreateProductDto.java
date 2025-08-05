package com.watches.backend.Dto.ProductDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Product name is required")
    @NotNull
    private String name;
    private String description;

    @NotBlank(message = "Product brand is required")
    @NotNull
    private String brand;

    @NotNull
    private Double size;

    @NotNull
    private Double weight;

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
 
}
