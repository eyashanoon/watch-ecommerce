package com.watches.backend.Dto.ProductDto;

public class CombinedPorductRequest {
    CreateProductDto createProductDto;
    ProductFeaturesDto  productFeaturesDto;

    public CreateProductDto getCreateProductDto() {
        return createProductDto;
    }

    public void setCreateProductDto(CreateProductDto createProductDto) {
        this.createProductDto = createProductDto;
    }

    public ProductFeaturesDto getProductFeaturesDto() {
        return productFeaturesDto;
    }

    public void setProductFeaturesDto(ProductFeaturesDto productFeaturesDto) {
        this.productFeaturesDto = productFeaturesDto;
    }
}
