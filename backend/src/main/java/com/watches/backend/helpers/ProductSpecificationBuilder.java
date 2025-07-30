package com.watches.backend.helpers;

import com.watches.backend.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationBuilder {

    private final List<Specification<Product>> specifications = new ArrayList<>();

    public ProductSpecificationBuilder withFilter(ProductQueryObject filter) {
        if(filter.getBrand() != null){
            specifications.add(
                    (root, query, cb) ->
                            cb.like(root.get("brand"), filter.getBrand())
            );
        }

        if(filter.getProductName() != null){
            specifications.add(
                    (root, query, cb) ->
                            cb.like(root.get("name"), filter.getProductName())
            );
        }

        if(filter.getProductDescription() != null){
            specifications.add(
                    (root, query, cb) ->
                            cb.like(root.get("description"), filter.getProductDescription())
            );
        }

        if(filter.getMinPrice() != -1) {
            specifications.add(
                    (root, query, cb) ->
                            cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice())
            );
        }

        if(filter.getMaxPrice() != Integer.MAX_VALUE) {
            specifications.add(
                    (root, query, cb) ->
                            cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice())
            );
        }

        return this;
    }

    public Specification<Product> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

}
