package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Helpers.ProductQueryObject;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    List<ProductDto> getAll(@Valid @ModelAttribute ProductQueryObject query){
        return repository.findAll().stream()
            .filter(p -> query.getProductName() == null || p.getName().contains(query.getProductName()))
            .filter(p -> query.getProductDescription() == null || p.getDescription().contains(query.getProductDescription()))
            .filter(p -> query.getBrand() == null || p.getBrand().contains(query.getBrand()))
            .filter(p -> query.getGender() == null || p.getBrand().contains(query.getBrand()))
            .filter(p -> query.getMaxprice() == Integer.MAX_VALUE || p.getPrice() <= query.getMaxprice())
            .filter((p -> query.getMinprice() <= -1 || p.getPrice() >= query.getMinprice()))
            .skip((long) (query.getPage() - 1) * query.getPageSize())
            .limit(query.getPageSize())
                .map(ProductMapper::toDto)
            .toList();
    }

    @GetMapping("/products/{id}")
    Product GetById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/products")
    Product Create(@Valid @RequestBody CreateProductDto productDto){
        return repository.save(ProductMapper.CreateToProduct(productDto));
    }

    @PutMapping("/products/{id}")
    Product update(@Valid @RequestBody CreateProductDto productDto, @PathVariable Long id){
        return repository.findById(id)
                .map(product -> {
                    product.setName(productDto.getName());
                    product.setBrand(productDto.getBrand());
                    product.setDescription(productDto.getDescription());
                    product.setDiscount(productDto.getDiscount());
                    product.setPrice(productDto.getPrice());
                    product.setGender(productDto.getGender());
                    product.setSize(productDto.getSize());
                    product.setType(productDto.getType());
                    product.setQuantity(productDto.getQuantity());
                    return repository.save(product);
                })
                .orElseGet(() -> repository.save(ProductMapper.CreateToProduct(productDto)));
    }

    @DeleteMapping("/products/{id}")
    void delete(@PathVariable Long id){
        repository.deleteById(id);
    }


}
