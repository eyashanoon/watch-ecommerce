package com.watches.backend.helpers.specification;

import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.query.Query;
import com.watches.backend.model.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.*;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications = new ArrayList<>();
    private static final Map<String, JoinPath> JOIN_PATH_MAP = new HashMap<>();

    static {
        JOIN_PATH_MAP.put("brand",           new JoinPath("brand", "name"));
        JOIN_PATH_MAP.put("shape",           new JoinPath("shape", "name"));
        JOIN_PATH_MAP.put("numberingFormat", new JoinPath("numberingFormat", "format"));
        JOIN_PATH_MAP.put("bandMaterial",    new JoinPath("band", "material"));
        JOIN_PATH_MAP.put("caseMaterial",    new JoinPath("aCase", "material"));
        JOIN_PATH_MAP.put("displayType",     new JoinPath("displayType", "type"));
        JOIN_PATH_MAP.put("handsColor",      new JoinPath("colors", "hands,color"));
        JOIN_PATH_MAP.put("backgroundColor", new JoinPath("colors", "background,color"));
        JOIN_PATH_MAP.put("bandColor",       new JoinPath("colors", "band,color"));
        JOIN_PATH_MAP.put("minPrice",        new JoinPath("price", null));
        JOIN_PATH_MAP.put("minSize",         new JoinPath("size", null));
        JOIN_PATH_MAP.put("minWeight",       new JoinPath("weight", null));
        JOIN_PATH_MAP.put("minQuantity",       new JoinPath("quantity", null));
    }

    public SpecificationBuilder<T> withFilter(Query filter) {

        if(filter == null) return this;

        Field[] fields = filter.getClass().getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);

            try{
                Object value = field.get(filter);

                if(Objects.isNull(value)) continue;

                String fieldName = field.getName();
                if(fieldName.contains("Color")){
                    addColorSpec(JOIN_PATH_MAP.get(fieldName), (String) value);
                    continue;
                }
                switch(field.getType().getName()){
                    case "java.lang.String":
                        addStringSpec((String) value, getExpressionProvider(fieldName));
                        break;
                    case "java.lang.Boolean":
                        addBoolSpec((Boolean) value, path(fieldName));
                        break;
                    case "java.lang.Double":
                        if(fieldName.startsWith("min")){
                            String maxFieldName = "max" + fieldName.substring(3);
                            Field maxField = filter.getClass().getDeclaredField(maxFieldName);
                            maxField.setAccessible(true);
                            Double maxValue = (Double) maxField.get(filter);
                             addRangeSpec((Double) value, maxValue, path(JOIN_PATH_MAP.get(fieldName).joinField()));
                        }
                        break;
                    default:
                        break;
                }
            }catch(IllegalAccessException | NoSuchFieldException e){
                System.out.println(e.getMessage());
            }
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> getExpressionProvider(String fieldName) {
        if(JOIN_PATH_MAP.containsKey(fieldName)){
            return joinAndGet(JOIN_PATH_MAP.get(fieldName).joinField(),JOIN_PATH_MAP.get(fieldName).getField());
        }else{
            return path(fieldName);
        }
    }

    private <J> Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<J>> path(String fieldName) {
        return (root, query, cb) -> root.get(fieldName);
    }

    private <J> Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<J>> joinAndGet(
            String joinField,
            String getField
    ) {
        return (root, query, cb) -> {
            Join<Product, J> join = root.join(joinField);
            return join.get(getField);
        };
    }

    private void addStringSpec(String value,
                               Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<String>> expressionProvider){

        if(!Utils.isNullOrWhiteSpace(value)) {
            String finalValue = Utils.normalizeString(value);
            specifications.add((root, query, cb) ->
                    cb.like(expressionProvider.apply(root, query, cb), "%" + finalValue + "%")
            );
        }
    }

    private void addBoolSpec(Boolean value,
                             Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<Boolean>> expressionProvider){

        if(Utils.validBooleanValue(value)) {
            specifications.add((root, query, cb) ->
                    cb.equal(expressionProvider.apply(root, query, cb), value)
            );
        }
    }

    private void addRangeSpec(Double minValue, Double maxValue,
                              Function3<Root<T>, CriteriaQuery<?>, CriteriaBuilder, Expression<Double>> expressionProvider){
        Boolean hasMin = !Objects.equals(minValue, -1.0);
        Boolean hasMax = !Objects.equals(maxValue, Double.MAX_VALUE);
        if(!hasMin && !hasMax) return;

        specifications.add((root, query, cb) -> {
            Expression<Double> path = expressionProvider.apply(root, query, cb);
            if(hasMin && hasMax) return cb.between(path, minValue, maxValue);
            else if(hasMin) return cb.greaterThanOrEqualTo(path, minValue);
            else return cb.lessThanOrEqualTo(path, maxValue);
        });
    }

    private void addColorSpec(JoinPath path, String value){
        String[] splits = path.getField().split(",");
        specifications.add((root, query, cb) -> {
            Join<T, ?> join = root.join(path.joinField());
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(join.get("watchPart"), splits[0]));
            if(!Utils.isNullOrWhiteSpace(value)){
                String finalValue = Utils.normalizeString(value);
                predicates.add(cb.equal(join.get("color"), finalValue));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
