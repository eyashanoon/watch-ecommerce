package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> getAll(@Valid @ModelAttribute ProductQueryObject query){
        List<Product> products = service.findAll(query);
        List<ProductDto> productDTO = products.stream()
                                            .map(ProductMapper::toDto)
                                            .toList();
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<ProductDto> GetById(@PathVariable Long id){
        Product product = service.findById(id);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @PostMapping("/products")
    ResponseEntity<Product> Create(@Valid @RequestBody CreateProductDto productDto){
        Product product = ProductMapper.createToProduct(productDto);
        URI location = service.create(product);
        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/products/{id}")
    ResponseEntity<ProductDto> update(@Valid @RequestBody CreateProductDto productDto,
                   @Valid @PathVariable Long id){
        Product product = service.update(productDto, id);
        return ResponseEntity.ok().body(ProductMapper.toDto(product));
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<Product> delete(@Valid @PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
