package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "feature_case")
public class Case extends BaseFeatureEntity {

    private String material;

    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<Product> products;

    public Case() {}

    public Case(String material) {
        this.material = material;
    }

}
