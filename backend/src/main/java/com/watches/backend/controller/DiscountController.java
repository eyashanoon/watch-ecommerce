package com.watches.backend.controller;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.Dto.DiscountDto.DiscountDto;
import com.watches.backend.mappers.DiscountMapper;
import com.watches.backend.model.Discount;
import com.watches.backend.service.DiscountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/discount")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService service;

            @PostMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('ADD_DISCOUNT')")
    List<Long> addDiscount(@Valid @RequestBody CreateDiscountDto dto) {
        Discount discount = service.createAsync(dto).join();
        return discount.getProductsId();
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_DISCOUNT')")
    List<DiscountDto> getAllDiscounts(){
        List<Discount> discounts = service.findAllAsync().join();
        return discounts.stream().map(DiscountMapper::toDiscountDto).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_DISCOUNT')")
    DiscountDto getDiscountById(@PathVariable Long id){
        Discount discount = service.findById(id).join();
        return DiscountMapper.toDiscountDto(discount);
    }

    @GetMapping("/by_product/{productId}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_DISCOUNT')")
    DiscountDto getDiscountByProductId(@PathVariable Long productId) {
        Discount discount = service.findByProductIdAsync(productId).join();
        return DiscountMapper.toDiscountDto(discount);
    }

    @PutMapping("/remove")
    @PreAuthorize("hasRole('OWNER') || hasRole('REMOVE_DISCOUNT')")
    ResponseEntity<?> removeDiscountFromProduct(@RequestBody List<Long> products) {
        service.removeProductsAsync(products);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('REMOVE_DISCOUNT')")
    DiscountDto deleteDiscount(@PathVariable Long id) {
        Discount discount = service.deleteByIdAsync(id).join();
        return DiscountMapper.toDiscountDto(discount);
    }

}
