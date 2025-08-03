package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.UpdateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.ProductSpecificationBuilder;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.*;
import com.watches.backend.service.productFeatures.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Async
public class ProductService {

    private final ProductRepository repository;

    private final ImageService imageService;
    private final BrandService brandService;
    private final BandService bandService;
    private final CaseService caseService;
    private final ColorService colorService;
    private final DisplayTypeService displayTypeService;
    private final NumberingFormatService numberingFormatService;
    private final ShapeService shapeService;

    public ProductService(ProductRepository repository,
                          ImageService imageService,
                          BrandService brandService,
                          BandService bandService,
                          CaseService caseService,
                          ColorService colorService,
                          DisplayTypeService displayTypeService,
                          NumberingFormatService numberingFormatService,
                          ShapeService shapeService) {
        this.repository = repository;
        this.imageService = imageService;
        this.brandService = brandService;
        this.bandService = bandService;
        this.caseService = caseService;
        this.colorService = colorService;
        this.displayTypeService = displayTypeService;
        this.numberingFormatService = numberingFormatService;
        this.shapeService = shapeService;
    }

    private void setFeatures(Product product, CreateProductDto dto) {

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
        imageService.create(dto.getImage()).thenAccept(product::setImage),
        bandService.create(dto.getBandMaterial()).thenAccept(product::setBand),
        brandService.create(dto.getBrand()).thenAccept(product::setBrand),
        caseService.create(dto.getCaseMaterial()).thenAccept(product::setACase),
        displayTypeService.create(dto.getDisplayType()).thenAccept(product::setDisplayType),
        numberingFormatService.create(dto.getNumberingFormat()).thenAccept(product::setNumberingFormat),
        shapeService.create(dto.getShape()).thenAccept(product::setShape)
        );

        allFutures.join();

        List<CompletableFuture<Color>> colorFutures = List.of(
                colorService.create("hands", dto.getHandsColor()),
                colorService.create("background", dto.getBackgroundColor()),
                colorService.create("band", dto.getBandColor())
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

    private void updateFeatures(Product product, UpdateProductDto dto) {

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                bandService.create(dto.getBandMaterial()).thenAccept(product::setBand),
                brandService.create(dto.getBrand()).thenAccept(product::setBrand),
                caseService.create(dto.getCaseMaterial()).thenAccept(product::setACase),
                displayTypeService.create(dto.getDisplayType()).thenAccept(product::setDisplayType),
                numberingFormatService.create(dto.getNumberingFormat()).thenAccept(product::setNumberingFormat),
                shapeService.create(dto.getShape()).thenAccept(product::setShape)
        );

        allFutures.join();

        List<CompletableFuture<Color>> colorFutures = List.of(
                colorService.create("hands", dto.getHandsColor()),
                colorService.create("background", dto.getBackgroundColor()),
                colorService.create("band", dto.getBandColor())
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
        CompletableFuture<Product> product = this.findByIdAsync(id); // throws ProductNotFoundException if not found

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

        CompletableFuture<Product> product = this.findByIdAsync(id); // throws ProductNotFoundException if not found

        product.thenAccept(
                repository::delete
        );
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
}
