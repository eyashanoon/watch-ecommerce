package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Band extends BaseFeatureEntity {

    private String material;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Product> products;

    public Band(String material) {
        this.material = material;
    }
}
