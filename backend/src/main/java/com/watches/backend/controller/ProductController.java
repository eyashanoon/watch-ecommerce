package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;
 
import org.springframework.http.ResponseEntity;
 
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    ResponseEntity<List<ProductDto>> getAll(@Valid @ModelAttribute ProductQueryObject query){

        CompletableFuture<List<Product>> products = service.findAllAsync(query);

        CompletableFuture<List<ProductDto>> productDTO = products.thenApply(l ->
                l.stream()
                        .map(ProductMapper::toDto)
                        .toList()
        );

        return ResponseEntity.ok()
                .body(productDTO.join()
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getById(@PathVariable Long id){

        CompletableFuture<Product> product = service.findByIdAsync(id);

        CompletableFuture<ProductDto> productDto = product.thenApply(
                ProductMapper::toDto
        );

        return ResponseEntity.ok()
                .body(productDto.join()
                );
    }

    @PostMapping
    ResponseEntity<Product> create(@Valid @ModelAttribute CreateProductDto createProductDto){

        CompletableFuture<Product> product = service.createAsync(createProductDto);

        return ResponseEntity.ok()
                .body(product.join()
                );
    }

    @PutMapping("/{id}")
    CompletableFuture<ResponseEntity<ProductDto>> update(@Valid @RequestBody CreateProductDto productDto,
                                                         @Valid @PathVariable Long id){

        CompletableFuture<Product> product = service.updateAsync(productDto, id);

        CompletableFuture<ProductDto> productDTO = product.thenApply(
                ProductMapper::toDto
        );

        return productDTO.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> delete(@Valid @PathVariable Long id){

        service.deleteByIdAsync(id);

        return ResponseEntity.noContent()
                .build();
    }


}
