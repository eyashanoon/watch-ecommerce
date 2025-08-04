package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NumberingFormat extends BaseFeatureEntity{

    private String format;

    @OneToMany(mappedBy = "numberingFormat", cascade = CascadeType.ALL)
    private List<Product> products;

    public NumberingFormat(String format) {
        this.format = format;
    }
}
