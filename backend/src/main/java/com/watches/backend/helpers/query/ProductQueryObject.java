package com.watches.backend.helpers.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductQueryObject implements Query {
    private String name = null;
    private String description = null;
    private String brand = null;
    private String numberingFormat;
    private String bandMaterial;
    private String caseMaterial;
    private String displayType;
    private String shape;
    private String handsColor;
    private String backgroundColor;
    private String bandColor;
    private Boolean includesDate = null;
    private Boolean hasFullNumerals = null;
    private Boolean hasTickingSound = null;
    private Boolean waterProof = null;
    private Boolean changeableBand = null;
    private Boolean deleted = false;
    private Double maxSize = Double.MAX_VALUE;
    private Double minSize = -1.0;
    private Double maxWeight = Double.MAX_VALUE;
    private Double minWeight = -1.0;
    private Double maxPrice = Double.MAX_VALUE;
    private Double minPrice = -1.0;
    private Integer page = 1;
    private Integer pageSize = 20;
}
