package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Shape extends BaseFeatureEntity {

    private String shape;

    @OneToMany(mappedBy = "shape", cascade = CascadeType.ALL)
    private List<Product> products;

    public Shape(){}

    public Shape(String shape) {
        this.shape = shape;
    }
}
