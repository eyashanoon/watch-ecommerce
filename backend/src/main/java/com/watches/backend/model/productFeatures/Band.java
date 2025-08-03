package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Band extends BaseFeatureEntity {

    private String bandMaterial;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Product> products;

    public Band() {
    }

    public Band(String bandMaterial) {
        this.bandMaterial = bandMaterial;
    }

}
