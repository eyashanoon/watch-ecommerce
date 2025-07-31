package com.watches.backend.helpers;

import com.watches.backend.model.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSpecificationBuilder {

    private final List<Specification<Product>> specifications = new ArrayList<>();

    public ProductSpecificationBuilder withFilter(ProductQueryObject filter) {

        addStringSpec(filter.getBrand(), joinAndGet("brand", "brand"));
        addStringSpec(filter.getShape(), joinAndGet("shape", "shape"));
        addStringSpec(filter.getBandMaterial(), joinAndGet("band", "band"));
        addStringSpec(filter.getCaseMaterial(), joinAndGet("aCase", "material"));
        addStringSpec(filter.getDisplayType(), joinAndGet("displayType", "type"));
        addStringSpec(filter.getNumberingFormat(), joinAndGet("numberingFormat", "numberingFormat"));

        addStringSpec(filter.getName(), path("name"));
        addStringSpec(filter.getDescription(), path("description"));

        addBoolSpec(filter.getIncludesDate(), path("includesDate"));
        addBoolSpec(filter.getHasFullNumerals(), path("hasFullNumerals"));
        addBoolSpec(filter.getHasTickingSound(), path("hasTickingSound"));
        addBoolSpec(filter.getWaterProof(), path("waterProof"));
        addBoolSpec(filter.getChangeableBand(), path("changeableBand"));

        addRangeSpec(filter.getMinPrice(), filter.getMaxPrice(), -1.0, Double.MAX_VALUE, path("price"));

        return this;
    }

    public Specification<Product> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private void addStringSpec(String value,
                               Function3<Root<Product>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> expressionProvider){

        if(!Utils.isNullOrWhiteSpace(value)) {
            specifications.add((root, query, cb) ->
                    cb.like(expressionProvider.apply(root, query, cb), value)
            );
        }
    }

    private void addBoolSpec(Boolean value,
                             Function3<Root<Product>, CriteriaQuery<?>, CriteriaBuilder, Expression<Boolean>> expressionProvider){

        if(Utils.validBooleanValue(value)) {
            specifications.add((root, query, cb) ->
                    cb.equal(expressionProvider.apply(root, query, cb), value)
            );
        }
    }

    private void addRangeSpec(Double minValue, Double maxValue, Double defaultMin, Double defaultMax,
                                                 Function3<Root<Product>, CriteriaQuery<?>, CriteriaBuilder, Expression<Double>> expressionProvider){
        Boolean hasMin = !Objects.equals(minValue, defaultMin);
        Boolean hasMax = !Objects.equals(maxValue, defaultMax);
        if(!hasMin && !hasMax) return;

        specifications.add((root, query, cb) -> {
            Expression<Double> path = expressionProvider.apply(root, query, cb);
            if(hasMin && hasMax) return cb.between(path, minValue, maxValue);
            else if(hasMin) return cb.greaterThanOrEqualTo(path, minValue);
            else return cb.lessThanOrEqualTo(path, maxValue);
        });
    }

    private <J> Function3<Root<Product>, CriteriaQuery<?>, CriteriaBuilder, Expression<J>> path(String fieldName) {
        return (root, query, cb) -> root.get(fieldName);
    }

    private <J> Function3<Root<Product>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> joinAndGet(
            String joinField,
            String getField
    ) {
        return (root, query, cb) -> {
            Join<Product, J> join = root.join(joinField);
            return join.get(getField);
        };
    }

    // an Interface to handle 3 arguments method
    @FunctionalInterface
    interface Function3<T, U, V, R>{
        R apply(T t, U u, V v);
    }

}
