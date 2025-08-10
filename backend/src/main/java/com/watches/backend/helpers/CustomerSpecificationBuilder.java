package com.watches.backend.helpers;

import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecificationBuilder {

    private final List<Specification<Customer>> specifications = new ArrayList<>();

    public CustomerSpecificationBuilder withFilter(CustomerQueryObject filter) {
        addStringSpec(filter.getUsername(), path("username"));
        addStringSpec(filter.getEmail(), path("email"));
        addStringSpec(filter.getPhone(), path("phone"));

        addBoolSpec(filter.isDeleted(), path("deleted"));

        return this;
    }

    public Specification<Customer> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private void addStringSpec(String value,
                               CustomerSpecificationBuilder.Function3<Root<Customer>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> expressionProvider){

        if(!Utils.isNullOrWhiteSpace(value)) {
            String finalValue = Utils.normalizeString(value);
            specifications.add((root, query, cb) ->
                    cb.like(expressionProvider.apply(root, query, cb), finalValue)
            );
        }
    }

    private void addBoolSpec(Boolean value,
                             CustomerSpecificationBuilder.Function3<Root<Customer>, CriteriaQuery<?>, CriteriaBuilder, Expression<Boolean>> expressionProvider){

        if(Utils.validBooleanValue(value)) {

            specifications.add((root, query, cb) ->
                    cb.equal(expressionProvider.apply(root, query, cb), value)
            );
        }
    }

    private <J> CustomerSpecificationBuilder.Function3<Root<Customer>, CriteriaQuery<?>, CriteriaBuilder, Expression<J>> path(String fieldName) {
        return (root, query, cb) -> root.get(fieldName);
    }

    @FunctionalInterface
    private interface Function3<T, U, V, R>{
        R apply(T t, U u, V v);
    }

}
