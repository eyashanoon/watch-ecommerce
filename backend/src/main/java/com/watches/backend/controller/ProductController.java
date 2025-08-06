package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.UpdateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

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
    Page<ProductDto> getAll(@Valid @ModelAttribute ProductQueryObject query){
        return service.findAllAsync(query)
                .thenApply(page -> page.map(ProductMapper::toDto))
                .join();
    }

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable Long id){
        CompletableFuture<Product> product = service.findByIdAsync(id);
        return ProductMapper.toDto(product.join());
    }

    @PostMapping
    ProductDto create(@Valid @ModelAttribute CreateProductDto createProductDto){

        CompletableFuture<Product> product = service.createAsync(createProductDto);

        return ProductMapper.toDto(product.join());
    }

    @PutMapping("/{id}")
    ProductDto update(@Valid @RequestBody UpdateProductDto productDto,
                      @Valid @PathVariable Long id){
 
        CompletableFuture<Product> product = service.updateAsync(productDto, id);

        CompletableFuture<ProductDto> productDTO = product.thenApply(
                ProductMapper::toDto
        );

        return productDTO.join();
    }

    @DeleteMapping("/{id}")
    void delete(@Valid @PathVariable Long id){
        service.deleteByIdAsync(id);
    }


}
