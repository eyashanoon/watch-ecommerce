package com.watches.backend.mappers;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.WishlistProductDto;
import com.watches.backend.Repositories.ImageRepository;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.Base64;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto(
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getImage().getData(),
                product.getSize(),
                product.getWaterProof(),
                product.getCrystal(),
                product.getColor(),
                product.getType(),
                product.getSupportsDate(),
                product.getNumberingFormat(),
                product.getHasFullNumerals(),
                product.getHasTickingSound(),
                product.getPrice(),
                product.getQuantity(),
                product.getDiscount()
        );

        return productDto;
    }

    public static Product WishlistDtoToProduct(WishlistProductDto productDto){

        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getDescription(),
                null,
                productDto.getSize(),
                productDto.getWaterProof(),
                productDto.getCrystal(),
                productDto.getColor(),
                productDto.getType(),
                productDto.getSupportsDate(),
                productDto.getNumberingFormat(),
                productDto.getHasFullNumerals(),
                productDto.getHasTickingSound(),
                productDto.getPrice(),
                productDto.getQuantity(),
                null
        );
    }

    public static Product createToProduct(CreateProductDto dto) {

        return new Product(
                dto.getName(),
                dto.getBrand(),
                dto.getDescription(),
                null,
                dto.getSize(),
                dto.getWaterProof(),
                dto.getCrystal(),
                dto.getColor(),
                dto.getType(),
                dto.getSupportsDate(),
                dto.getNumberingFormat(),
                dto.getHasFullNumerals(),
                dto.getHasTickingSound(),
                dto.getPrice(),
                dto.getQuantity(),
                null
        );
    }

}
