package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.factories.ProductFilterFactory;
import com.watches.backend.helpers.productOptions.IProductFilter;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@Async
public class ProductService {

    private final ProductRepository repository;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public CompletableFuture<Product> createAsync(CreateProductDto productDto) {
        Product product = ProductMapper.createToProduct(productDto);
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
            p.setDiscount(createProductDto.getDiscount());
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
