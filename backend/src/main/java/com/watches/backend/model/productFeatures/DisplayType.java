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
public class DisplayType extends BaseFeatureEntity {

    private String type;

    @OneToMany(mappedBy = "displayType", cascade = CascadeType.ALL)
    private List<Product> products;

    public DisplayType() {}

    public DisplayType(String type) {
        this.type = type;
    }

}
