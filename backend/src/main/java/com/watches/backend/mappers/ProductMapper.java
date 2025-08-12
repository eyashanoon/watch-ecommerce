package com.watches.backend.mappers;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.*;

public class ProductMapper {

    public static ProductDto toDto(Product product){

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                (product.getImage() == null ? null : product.getImage().stream().map(i->i.getId()).toList()),
                product.getBrand().getName(),
                product.getColors()
                        .get(0)
                        .getColor(),
                product.getColors()
                        .get(1)
                        .getColor(),
                product.getColors()
                        .get(2)
                        .getColor(),
                product.getNumberingFormat().getFormat(),
                product.getBand().getMaterial(),
                product.getACase().getMaterial(),
                product.getDisplayType().getType(),
                product.getShape().getName(),
                product.getIncludesDate(),
                product.getHasFullNumerals(),
                product.getHasTickingSound(),
                product.getWaterProof(),
                product.getChangeableBand(),
                product.getSize(),
                product.getWeight(),
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
