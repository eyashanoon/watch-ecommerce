package com.watches.backend.controller;

import com.watches.backend.Dto.CreateProductDto;
import com.watches.backend.Dto.ProductDto;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    List<ProductDto> getAll(){
        return repository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    Product GetById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/products")
    Product Create(@Valid @RequestBody CreateProductDto productDto){
        return repository.save(ProductMapper.toEntity(productDto));
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
                .orElseGet(() -> {
                    return repository.save(ProductMapper.toEntity(productDto));
                });
    }

    @DeleteMapping("/products/{id}")
    void delete(@PathVariable Long id){
        repository.deleteById(id);
    }


}
