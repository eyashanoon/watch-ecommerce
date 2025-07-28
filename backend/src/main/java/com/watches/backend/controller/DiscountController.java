package com.watches.backend.controller;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.model.Discount;
import com.watches.backend.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/discounts")
@Async
public class DiscountController {

    private final DiscountService service;

    public DiscountController(DiscountService service) {
        this.service = service;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Discount>>> getAll(){
        CompletableFuture<List<Discount>> discounts = service.findAllAsync();

        return discounts.thenApply(discount ->
                ResponseEntity.ok()
                        .body(discount)
        );
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Discount>> getById(@Valid @PathVariable Long id){
        CompletableFuture<Discount> discount = service.findByIdAsync(id);
        return discount.thenApply(d ->
                ResponseEntity.ok().body(d)
        );
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Discount>> create(@Valid @RequestBody CreateDiscountDto discountDto){
        CompletableFuture<Discount> discount = service.create(discountDto);
        return discount.thenApply(d ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(d)
        );
    }


}
