package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.helpers.factories.ProductFilterFactory;
import com.watches.backend.helpers.productOptions.IProductFilter;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final ProductRepository repository;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }



    public Product create(CreateProductDto productDto) {
        Product product = ProductMapper.createToProduct(productDto);
        repository.save(product);
        return product;
    }


    public Product update(CreateProductDto createProductDto, Long id) {
        Product product = this.findById(id); // throws ProductNotFoundException if not found

        product.setName(createProductDto.getName());
        product.setDescription(createProductDto.getDescription());
        product.setPrice(createProductDto.getPrice());
        product.setQuantity(createProductDto.getQuantity());
        product.setType(createProductDto.getType());
        product.setDiscount(createProductDto.getDiscount());
        product.setBrand(createProductDto.getBrand());

        return repository.save(product);
    }


    public void delete(Long id) {
        Product product = this.findById(id); // throws ProductNotFoundException if not found
        repository.delete(product);
    }


    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    public List<Product> findAll(ProductQueryObject queryObject) {
        // Filters factory
        // it takes the product query and generate filters depending on filters user applied
        // then using the for-loop it applies the filters
        List< IProductFilter> filters = ProductFilterFactory.getFilters(queryObject);
        Stream<Product> products = repository.findAll().stream();
        for (IProductFilter filter : filters) {
            products = filter.applyFilter(products, queryObject);
        }
        // skip number of pages
        products = products.skip((long) (queryObject.getPage() - 1) * queryObject.getPageSize());
        // reduce the number of products to fit in the page size
        products = products.limit(queryObject.getPageSize());

        return products.toList();
    }
}
