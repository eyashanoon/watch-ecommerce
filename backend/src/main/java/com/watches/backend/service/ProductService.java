package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.ProductSpecificationBuilder;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.*;
import com.watches.backend.service.productFeatures.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.awt.image.BandedSampleModel;
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
        CompletableFuture<Image> image = imageService.create(dto.getImage());
        CompletableFuture<Band> band = bandService.create(dto.getBandMaterial());
        CompletableFuture<Brand> brand = brandService.create(dto.getBrand());
        CompletableFuture<Case> productCase = caseService.create(dto.getCaseMaterial());
        CompletableFuture<DisplayType> displayType = displayTypeService.create(dto.getDisplayType());
        CompletableFuture<NumberingFormat> numberingFormat = numberingFormatService.create(dto.getNumberingFormat());
        CompletableFuture<Shape> shape = shapeService.create(dto.getShape());

        image.thenAccept(product::setImage);
        band.thenAccept(product::setBand);
        brand.thenAccept(product::setBrand);
        productCase.thenAccept(product::setACase);
        displayType.thenAccept(product::setDisplayType);
        numberingFormat.thenAccept(product::setNumberingFormat);
        shape.thenAccept(product::setShape);

        List<CompletableFuture<Color>> colorFutures = List.of(
                colorService.create("hands", dto.getHandsColor()),
                colorService.create("background", dto.getBackgroundColor()),
                colorService.create("band", dto.getBandColor())
        );

        colorFutures.forEach(cf -> cf.thenAccept(color -> product.getColors().add(color)));

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

    public CompletableFuture<Product> updateAsync(CreateProductDto createProductDto, Long id) {
//        CompletableFuture<Product> product = this.findByIdAsync(id); // throws ProductNotFoundException if not found
//
//        product = product.thenApply(p -> {
//            p.setName(createProductDto.getName());
//            p.setDescription(createProductDto.getDescription());
//            p.setPrice(createProductDto.getPrice());
//            p.setQuantity(createProductDto.getQuantity());
//            p.setType(createProductDto.getType());
//            p.setBrand(createProductDto.getBrand());
//            return repository.save(p);
//        });

        return null;
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

    public CompletableFuture<List<Product>> findAllAsync(ProductQueryObject queryObject) {
        // Filters factory
        // it takes the product query and generate filters depending on filters user applied
        // then using the for-loop it applies the filters

//       Specification<Product> spec = new ProductSpecificationBuilder()
//               .withFilter(queryObject)
//               .build();
//
//       Pageable pg = PageRequest.of(queryObject.getPage(), queryObject.getPageSize());
//       Page<Product> products = repository.findAll(spec, pg);



        return CompletableFuture.completedFuture(
                repository.findAll().stream().toList()
        );
    }
}
