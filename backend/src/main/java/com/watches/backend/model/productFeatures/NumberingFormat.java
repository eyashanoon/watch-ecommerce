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
public class NumberingFormat extends BaseFeatureEntity{

    private String numberingFormat;

    @OneToMany(mappedBy = "numberingFormat", cascade = CascadeType.ALL)
    private List<Product> products;

    public NumberingFormat() {
    }

    public NumberingFormat(String numberingFormat) {
        this.numberingFormat = numberingFormat;
    }

}
