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
                product.getGender(),
                product.getSize(),
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
                productDto.getGender(),
                productDto.getSize(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getDiscount()
        );
    }

    public static Product CreateToProduct(CreateProductDto dto){
        return new Product(
                dto.getName(),
                dto.getBrand(),
                dto.getDescription(),
                dto.getType(),
                dto.getGender(),
                dto.getSize(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getDiscount()
        );
    }

}
