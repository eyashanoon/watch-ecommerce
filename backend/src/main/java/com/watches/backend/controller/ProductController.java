package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.model.Product;
import com.watches.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    ResponseEntity<List<ProductDto>> getAll(@Valid @ModelAttribute ProductQueryObject query){
        List<Product> products = service.findAll(query);
        List<ProductDto> productDTO = products.stream()
                                            .map(ProductMapper::toDto)
                                            .toList();
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> GetById(@PathVariable Long id){
        Product product = service.findById(id);
        ProductDto productDto = ProductMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    ResponseEntity<Product> Create(@Valid @RequestBody CreateProductDto productDto){
        Product product = service.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> update(@Valid @RequestBody CreateProductDto productDto,
                   @Valid @PathVariable Long id){
        Product product = service.update(productDto, id);
        return ResponseEntity.ok().body(ProductMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> delete(@Valid @PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
