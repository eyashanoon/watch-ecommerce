package com.watches.backend.mappers;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        return new ProductDto(
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getType(),
                product.getPrice(),
                product.getQuantity(),
                product.getDiscount()
        );
    }

    public static Product DtoToProduct(ProductDto productDto){
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getDescription(),
                productDto.getType(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getDiscount()
        );
    }

    public static Product createToProduct(CreateProductDto dto){
        return new Product(
                dto.getName(),
                dto.getBrand(),
                dto.getDescription(),
                dto.getType(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getDiscount()
        );
    }

}
