package com.watches.backend.Dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> imageId;
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
    private Double originalPrice;
    private Double discountPrice;
    private Double discount;
    private Integer quantity;
}
