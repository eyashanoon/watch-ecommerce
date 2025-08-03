package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Color extends BaseFeatureEntity {

    private String watchPart;
    private String color;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Color(){};

    public Color(String watchPart, String color) {
        this.watchPart = watchPart;
        this.color = color;
    }

}
