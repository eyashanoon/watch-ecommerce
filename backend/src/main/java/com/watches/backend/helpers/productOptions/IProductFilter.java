package com.watches.backend.helpers.productOptions;

import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.model.Product;

import java.util.stream.Stream;

public interface IProductFilter {
    Stream<Product> applyFilter(Stream<Product> products, ProductQueryObject queryObject);
}
