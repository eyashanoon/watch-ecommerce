package com.watches.backend.controller;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.Dto.DiscountDto.DiscountDto;
import com.watches.backend.mappers.DiscountMapper;
import com.watches.backend.model.Discount;
import com.watches.backend.service.DiscountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/discount")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService service;

    @PostMapping
    List<Long> addDiscount(@Valid @RequestBody CreateDiscountDto dto) {
        Discount discount = service.createAsync(dto).join();
        return discount.getProductsId();
    }

    @GetMapping
    List<DiscountDto> getAllDiscounts(){
        List<Discount> discounts = service.findAllAsync().join();
        return discounts.stream().map(DiscountMapper::toDiscountDto).toList();
    }

    @GetMapping("/{id}")
    DiscountDto getDiscountById(@PathVariable Long id){
        Discount discount = service.findById(id).join();
        return DiscountMapper.toDiscountDto(discount);
    }

    @GetMapping("/by_product/{productId}")
    DiscountDto getDiscountByProductId(@PathVariable Long productId) {
        Discount discount = service.findByProductIdAsync(productId).join();
        return DiscountMapper.toDiscountDto(discount);
    }

    @PutMapping("/remove")
    ResponseEntity<?> removeDiscountFromProduct(@RequestBody List<Long> products) {
        service.removeProductsAsync(products);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    DiscountDto removeDiscountFromProduct(@PathVariable Long id) {
        Discount discount = service.deleteByIdAsync(id).join();
        return DiscountMapper.toDiscountDto(discount);
    }

}
