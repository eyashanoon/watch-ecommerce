package com.watches.backend.helpers.factories;

import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.helpers.StringUtils;
import com.watches.backend.helpers.productOptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductFilterFactory {

    public static List<IProductFilter> getFilters(ProductQueryObject queryObject){
        List<IProductFilter> filters = new ArrayList<>();
        if(!StringUtils.isNullOrWhiteSpace(queryObject.getProductName())){
            filters.add(new ProductNameFilter());
        }
        if(!Objects.isNull(queryObject.getProductDescription())){
            filters.add(new ProductDescriptionFilter());
        }
        if(!StringUtils.isNullOrWhiteSpace(queryObject.getBrand())){
            filters.add(new ProductBrandFilter());
        }
        if(queryObject.getMaxprice() < Integer.MAX_VALUE || queryObject.getMinprice() > -1){
            filters.add(new ProductPriceFilter());
        }


        return filters;
    }

}
