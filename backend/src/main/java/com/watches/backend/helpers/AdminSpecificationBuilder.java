package com.watches.backend.helpers;

import com.watches.backend.model.Admin;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdminSpecificationBuilder {

    private final List<Specification<Admin>> specifications = new ArrayList<>();

    public AdminSpecificationBuilder withFilter(AdminQueryObject filter) {
        addStringSpec(filter.getUsername(), path("username"));
        addStringSpec(filter.getEmail(), path("email"));
        addStringSpec(filter.getPhone(), path("phone"));

        addBoolSpec(filter.isDeleted(), path("deleted"));

        return this;
    }

    public Specification<Admin> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private void addStringSpec(String value,
                               AdminSpecificationBuilder.Function3<Root<Admin>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> expressionProvider){

        if(!Utils.isNullOrWhiteSpace(value)) {
            String finalValue = Utils.normalizeString(value);
            specifications.add((root, query, cb) ->
                    cb.like(expressionProvider.apply(root, query, cb), finalValue)
            );
        }
    }

    private void addBoolSpec(Boolean value,
                             AdminSpecificationBuilder.Function3<Root<Admin>, CriteriaQuery<?>, CriteriaBuilder, Expression<Boolean>> expressionProvider){

        if(Utils.validBooleanValue(value)) {

            specifications.add((root, query, cb) ->
                    cb.equal(expressionProvider.apply(root, query, cb), value)
            );
        }
    }

    private <J> AdminSpecificationBuilder.Function3<Root<Admin>, CriteriaQuery<?>, CriteriaBuilder, Expression<J>> path(String fieldName) {
        return (root, query, cb) -> root.get(fieldName);
    }

    @FunctionalInterface
    interface Function3<T, U, V, R>{
        R apply(T t, U u, V v);
    }

}
