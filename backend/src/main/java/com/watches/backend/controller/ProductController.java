package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
 
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
@EnableMethodSecurity
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<ProductDto> getAll(@Valid @ModelAttribute ProductQueryObject query){

        CompletableFuture<Page<Product>> products = service.findAllAsync(query);

        CompletableFuture<List<ProductDto>> productDTO = products.thenApply(l ->
                l.stream()
                        .map(ProductMapper::toDto)
                        .toList()
        );

        return productDTO.join();
    }

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable Long id){

        CompletableFuture<Product> product = service.findByIdAsync(id);

        CompletableFuture<ProductDto> productDto = product.thenApply(
                ProductMapper::toDto
        );

        return productDto.join();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    Product create(@Valid @ModelAttribute CreateProductDto createProductDto){

        CompletableFuture<Product> product = service.createAsync(createProductDto);

        return product.join();
    }

    @PutMapping("/{id}")
    ProductDto update(@Valid @ModelAttribute CreateProductDto productDto, @Valid @PathVariable Long id){

        CompletableFuture<Product> product = service.updateAsync(productDto, id);

        CompletableFuture<ProductDto> productDTO = product.thenApply(
                ProductMapper::toDto
        );

        return productDTO.join();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> delete(@Valid @PathVariable Long id){

        service.deleteByIdAsync(id);

        return ResponseEntity.noContent()
                .build();
    }


}
