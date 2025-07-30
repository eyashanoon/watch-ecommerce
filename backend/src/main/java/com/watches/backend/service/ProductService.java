package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.factories.ProductFilterFactory;
import com.watches.backend.helpers.productOptions.IProductFilter;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@Async
public class ProductService {

    private final ProductRepository repository;
    private final ImageService imageService;

    public ProductService(ProductRepository repository,
                          ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }


    public CompletableFuture<Product> createAsync(CreateProductDto productDto){
        CompletableFuture<Image> productImage = imageService.createImage(productDto.getImage());

        Product product = ProductMapper.createToProduct(productDto);
        productImage.thenAccept(product::setImage);
        repository.save(product);
        return CompletableFuture.completedFuture(product);
    }

    public CompletableFuture<Product> updateAsync(CreateProductDto createProductDto, Long id) {
        CompletableFuture<Product> product = this.findByIdAsync(id); // throws ProductNotFoundException if not found

        product = product.thenApply(p -> {
            p.setName(createProductDto.getName());
            p.setDescription(createProductDto.getDescription());
            p.setPrice(createProductDto.getPrice());
            p.setQuantity(createProductDto.getQuantity());
            p.setType(createProductDto.getType());
            p.setBrand(createProductDto.getBrand());
            return repository.save(p);
        });

        return product.thenApply(p -> p);
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
       List< IProductFilter> filters = ProductFilterFactory.getFilters(queryObject);

        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("brand"), queryObject.getBrand());
            }
        };
//        PageRequest.of()
        Stream<Product> products = repository.findAll().stream();

        for (IProductFilter filter : filters) {
            products = filter.applyFilter(products, queryObject);
        }

        // skip number of pages
        products = products.skip(
                (long) (queryObject.getPage() - 1) * queryObject.getPageSize()
        );

        // reduce the number of products to fit in the page size
        products = products.limit(
                queryObject.getPageSize()
        );

        return CompletableFuture.completedFuture(
                products.toList()
        );
    }
}
