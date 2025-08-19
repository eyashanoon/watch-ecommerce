package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Color extends BaseFeatureEntity {

    private String watchPart;
    private String color;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Color(String watchPart, String color) {
        this.watchPart = watchPart;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.equals(getWatchPart(), color1.getWatchPart()) && Objects.equals(getColor(), color1.getColor()) && Objects.equals(getProduct(), color1.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWatchPart(), getColor(), getProduct());
    }
}
