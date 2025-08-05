package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.UpdateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.ProductSpecificationBuilder;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.*;
import com.watches.backend.service.productFeatures.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Async
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final BrandService brandService;
    private final BandService bandService;
    private final CaseService caseService;
    private final ColorService colorService;
    private final DisplayTypeService displayTypeService;
    private final NumberingFormatService numberingFormatService;
    private final ShapeService shapeService;

    private void getFeaturesList(Product product, String bandMaterial, String brand, String caseMaterial, String displayType, String numberingFormat, String shape, String handsColor, String backgroundColor, String bandColor) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                bandService.create(bandMaterial).thenAccept(product::setBand),
                brandService.create(brand).thenAccept(product::setBrand),
                caseService.create(caseMaterial).thenAccept(product::setACase),
                displayTypeService.create(displayType).thenAccept(product::setDisplayType),
                numberingFormatService.create(numberingFormat).thenAccept(product::setNumberingFormat),
                shapeService.create(shape).thenAccept(product::setShape)
        );

        allFutures.join();

        List<CompletableFuture<Color>> colorFutures = List.of(
                colorService.create("hands", handsColor),
                colorService.create("background", backgroundColor),
                colorService.create("band", bandColor)
        );

        CompletableFuture<Void> allColorFutures = CompletableFuture.allOf(
                colorFutures.stream()
                        .map(future ->
                                future.thenAccept(
                                        product.getColors()::add)
                        )
                        .toArray(CompletableFuture[]::new)
        );
        allColorFutures.join();
    }

    private void setFeatures(Product product, CreateProductDto dto) {

        getFeaturesList(product,
                dto.getBandMaterial(),
                dto.getBrand(),
                dto.getCaseMaterial(),
                dto.getDisplayType(),
                dto.getNumberingFormat(),
                dto.getShape(),
                dto.getHandsColor(),
                dto.getBackgroundColor(),
                dto.getBandColor());
    }

    private void updateFeatures(Product product, UpdateProductDto dto) {

        getFeaturesList(product,
                dto.getBandMaterial(),
                dto.getBrand(),
                dto.getCaseMaterial(),
                dto.getDisplayType(),
                dto.getNumberingFormat(),
                dto.getShape(),
                dto.getHandsColor(),
                dto.getBackgroundColor(),
                dto.getBandColor());

    }



    public CompletableFuture<Product> createAsync(CreateProductDto productDto){

        Product product = ProductMapper.createToProduct(productDto);
        setFeatures(product, productDto);

        repository.save(product);

        for(Color color : product.getColors()){
            color.setProduct(product);
            colorService.update(color);
        }

        return CompletableFuture.completedFuture(product);
    }

    public CompletableFuture<Product> updateAsync(UpdateProductDto createProductDto, Long id) {
        CompletableFuture<Product> product = this.findByIdAsync(id);

        product = product.thenApply(p -> {
            p.setName(createProductDto.getName());
            p.setDescription(createProductDto.getDescription());
            p.setPrice(createProductDto.getPrice());
            p.setQuantity(createProductDto.getQuantity());
            p.setSize(createProductDto.getSize());
            p.setWeight(createProductDto.getWeight());
            p.setChangeableBand(createProductDto.getChangeableBand());
            p.setHasFullNumerals(createProductDto.getHasFullNumerals());
            p.setHasTickingSound(createProductDto.getHasTickingSound());
            p.setIncludesDate(createProductDto.getIncludesDate());
            p.setWaterProof(createProductDto.getWaterProof());

            updateFeatures(p, createProductDto);

            return repository.save(p);
        });

        return product;
    }

    public void deleteByIdAsync(Long id) {
        CompletableFuture<Product> product = this.findByIdAsync(id);
        product.thenAccept(p -> p.setDeleted(true));
        product.thenAccept(repository::save);
    }

    public CompletableFuture<Product> findByIdAsync(Long id) {
        return CompletableFuture.completedFuture(
                repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                )
        );
    }

    public CompletableFuture<Page<Product>> findAllAsync(ProductQueryObject queryObject) {

       Specification<Product> spec = new ProductSpecificationBuilder()
               .withFilter(queryObject)
               .build();

       Page<Product> products = repository.findAll(spec,
               PageRequest.of(queryObject.getPage() - 1, queryObject.getPageSize())
       );

       return CompletableFuture.completedFuture(products);
    }

    public void setImage(Product product, Image image){
        product.setImage(image);
        repository.save(product);
    }

}
