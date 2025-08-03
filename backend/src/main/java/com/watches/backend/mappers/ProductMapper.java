package com.watches.backend.mappers;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.WishlistProductDto;
import com.watches.backend.Repositories.ImageRepository;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.*;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.Base64;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto(
                product.getName(),
                product.getDescription(),
                 (product.getImage() == null ? "" : product.getImage().getData()),
                product.getBrand().getBrand(),
 
                product.getSize(),
                product.getWeight(),
                product.getColors()
                        .get(0)
                        .getColor(),
                product.getColors()
                        .get(1)
                        .getColor(),
                product.getColors()
                        .get(2)
                        .getColor(),
                product.getNumberingFormat().getNumberingFormat(),
                product.getBand().getBandMaterial(),
                product.getACase().getMaterial(),
                product.getDisplayType().getType(),
                product.getShape().getShape(),
                product.getIncludesDate(),
                product.getHasFullNumerals(),
                product.getHasTickingSound(),
                product.getWaterProof(),
                product.getChangeableBand(),
                product.getPrice(),
                 product.getQuantity()
 
        );
    }

    public static Product createToProduct(CreateProductDto dto) {


        return new Product(
                dto.getName(),
                dto.getDescription(),
                null,
                dto.getWaterProof(),
                dto.getHasTickingSound(),
                dto.getIncludesDate(),
                dto.getHasFullNumerals(),
                dto.getChangeableBand(),
                null,
                null,
                null,
                null,
                null,
                null,
                dto.getWeight(),
                dto.getSize(),
                null,
                dto.getPrice(),
                dto.getQuantity()
        );
    }

    public static Product dtoToProduct(ProductDto productDto) {

        Band band = new Band(productDto.getBandMaterial());

        Brand brand = new Brand(productDto.getBrand());
        Case casee = new Case(productDto.getCaseMaterial());
        Shape shape = new Shape(productDto.getShape());
        NumberingFormat nf = new NumberingFormat(productDto.getNumberingFormat());
        DisplayType dt = new DisplayType(productDto.getDisplayType());

        return new Product(
                productDto.getName(),
                productDto.getDescription(),
                null,
                productDto.getWaterProof(),
                productDto.getHasTickingSound(),
                productDto.getIncludesDate(),
                productDto.getHasFullNumerals(),
                productDto.getChangeableBand(),
                nf,
                brand,
                shape,
                band,
                null,
                dt,
                productDto.getWeight(),
                productDto.getSize(),
                casee,
                productDto.getPrice(),
                productDto.getQuantity()
        );
    }
}
