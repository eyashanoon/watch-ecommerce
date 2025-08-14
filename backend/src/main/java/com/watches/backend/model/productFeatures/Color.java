package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Color extends BaseFeatureEntity {

    private String watchPart;
    private String color;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Color(String watchPart, String color) {
        this.watchPart = watchPart;
        this.color = color;
    }

}
