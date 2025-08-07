package com.watches.backend.model;

import com.watches.backend.model.productFeatures.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private Boolean changeableBand;
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "numbering_format_id", referencedColumnName = "id")
    private NumberingFormat numberingFormat;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private List<Color> colors;

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
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private Case aCase;

    @ManyToOne
    @JoinColumn(name = "display_type_id", referencedColumnName = "id")
    private DisplayType displayType;


    private Double weight;
    private Double size;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer quantity;

    public Product(String name,
                   String description,
                   Image image,
                   Boolean waterProof,
                   Boolean hasTickingSound,
                   Boolean includesDate,
                   Boolean hasFullNumerals,
                   Boolean changeableBand,
                   NumberingFormat numberingFormat,
                   Brand brand,
                   Shape shape,
                   Band band,
                   Discount discount,
                   DisplayType displayType,
                   Double weight,
                   Double size,
                   Case aCase,
                   Double price,
                   Integer quantity) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.waterProof = waterProof;
        this.hasTickingSound = hasTickingSound;
        this.includesDate = includesDate;
        this.hasFullNumerals = hasFullNumerals;
        this.changeableBand = changeableBand;
        this.numberingFormat = numberingFormat;
        this.brand = brand;
        this.shape = shape;
        this.band = band;
        this.discount = discount;
        this.displayType = displayType;
        this.weight = weight;
        this.size = size;
        this.aCase = aCase;
        this.price = price;
        this.quantity = quantity;
        this.colors = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, waterProof, hasTickingSound, includesDate, hasFullNumerals, size, brand, colors, shape, band, discount, weight, price, quantity);
    }



 }
 
