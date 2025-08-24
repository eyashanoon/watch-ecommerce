package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.UpdateProductDto;
import com.watches.backend.helpers.query.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
@EnableMethodSecurity
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    Page<ProductDto> getAll(@Valid @ModelAttribute ProductQueryObject query){
        return service.findAllAsync(query)
                .thenApply(page -> page.map(ProductMapper::toDto))
                .join();
    }

    @GetMapping("/products/name/{name}")
    public  List<ProductDto> getAllWithProductName(@PathVariable String name) {
        return service.findAllByNameAsync(name).join();
    }


    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('SEE_PRODEUCT')")
    ProductDto getById(@PathVariable Long id){
        CompletableFuture<Product> product = service.findByIdAsync(id);
        return ProductMapper.toDto(product.join());
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('CREATE_PRODUCT')")
    ProductDto create(@RequestBody  CreateProductDto createProductDto){

        CompletableFuture<Product> product = service.createAsync(createProductDto);

        return ProductMapper.toDto(product.join());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('UPDATE_PRODUCT')")
    ProductDto update(@Valid @RequestBody UpdateProductDto productDto,
                      @Valid @PathVariable Long id){
 
        CompletableFuture<Product> product = service.updateAsync(productDto, id);

        CompletableFuture<ProductDto> productDTO = product.thenApply(
                ProductMapper::toDto
        );

        return productDTO.join();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('DELETE_PRODUCT')")
    void delete(@Valid @PathVariable Long id){
        service.deleteByIdAsync(id);
    }
}
