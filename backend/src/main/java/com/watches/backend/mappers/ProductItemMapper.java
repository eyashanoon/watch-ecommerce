package com.watches.backend.mappers;

import com.watches.backend.Dto.productItemDto.ProductItemDto;
import com.watches.backend.model.ProductItem;

public class ProductItemMapper {

    public static ProductItemDto toProductItemDto(ProductItem product){
        return new ProductItemDto(
                product.getProduct().getId(),
                product.getQuantity(),
                product.getPrice()
        );
    }

}
