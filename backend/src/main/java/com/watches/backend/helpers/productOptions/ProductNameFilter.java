package com.watches.backend.helpers.productOptions;

import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.model.Product;

import java.util.stream.Stream;

public class ProductNameFilter implements IProductFilter {

    @Override
    public Stream<Product> applyFilter(Stream<Product> products, ProductQueryObject queryObject) {
        return products.filter(p -> p.getName().contains(queryObject.getProductName()));
    }
}
