package com.watches.backend.helpers;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductQueryObject {
    private String name = null;
    private String description = null;
    private String brand = null;
    private String numberingFormat;
    private String bandMaterial;
    private String caseMaterial;
    private String DisplayType;
    private String shape;
    private Boolean includesDate = null;
    private Boolean hasFullNumerals = null;
    private Boolean hasTickingSound = null;
    private Boolean waterProof = null;
    private Boolean changeableBand = null;
    private Double maxPrice = Double.MAX_VALUE;
    private Double minPrice = -1.0;
    private Integer page = 1;
    private Integer pageSize = 20;
}
