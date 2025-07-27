package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CombinedPorductRequest;
import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.ProductFeaturesDto;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
@Async
@EnableMethodSecurity
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    CompletableFuture<ResponseEntity<List<ProductDto>>> getAll(@Valid @ModelAttribute ProductQueryObject query){

        CompletableFuture<List<Product>> products = service.findAllAsync(query);

        CompletableFuture<List<ProductDto>> productDTO = products.thenApply(l ->
                l.stream()
                        .map(ProductMapper::toDto)
                        .toList()
        );

        return productDTO.thenApply(l ->
                ResponseEntity.ok()
                        .body(l)
        );
    }

    @GetMapping("/{id}")
    CompletableFuture<ResponseEntity<ProductDto>> GetById(@PathVariable Long id){
        CompletableFuture<Product> product = service.findByIdAsync(id);

        CompletableFuture<ProductDto> productDto = product.thenApply(
                ProductMapper::toDto
        );

        return productDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @PostMapping
    CompletableFuture<ResponseEntity<Product>> Create(@Valid @RequestBody CombinedPorductRequest combinedPorductRequest){

        CreateProductDto productDto = combinedPorductRequest.getCreateProductDto();
        Map<String, Object> tags = ProductFeaturesDto.getAllTags(
                combinedPorductRequest.getProductFeaturesDto()
        );

        CompletableFuture<Product> product = service.createAsync(productDto);

        product.thenAccept(p -> p.setTags(tags));

        return product.thenApply(p ->
                ResponseEntity.ok()
                        .body(p)
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
    CompletableFuture<ResponseEntity<Product>> delete(@Valid @PathVariable Long id){

        service.deleteByIdAsync(id);

        return CompletableFuture.completedFuture(
                ResponseEntity.noContent()
                        .build()
        );
    }


}
