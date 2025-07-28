package com.watches.backend.controller;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
 import com.watches.backend.Repositories.*;
import com.watches.backend.exceptions.CartNotFoundException;
import com.watches.backend.exceptions.CustomerNotFoundException;
import com.watches.backend.exceptions.ProductNotFoundException;
 
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.Customer;
import com.watches.backend.model.ProductItem;
import com.watches.backend.service.CartService;
import jakarta.validation.Valid;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
 
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/carts")
@Async
public class CartController {
 
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
 
    }

    @GetMapping("/{id}")
    CompletableFuture<ResponseEntity<CartDto>> GetCartById(@PathVariable Long id){

        CompletableFuture<Cart> cart = service.findByIdAsync(id);

        CompletableFuture<CartDto> cartDto =  cart.thenApply(
                CartMapper::toCartDto
        );

        return cartDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }
 
    @PostMapping
    CompletableFuture<ResponseEntity<Cart>> CreateCart(@Valid @RequestBody CreateCartDto cartDto){

        CompletableFuture<Cart> cart = service.createAsync(cartDto);

        return cart.thenApply(c ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(c)
        );
 

    }

    @PutMapping("/addItem/{id}")
    CompletableFuture<ResponseEntity<CartDto>> UpdateCart(@Valid @PathVariable Long id,
                                                          @Valid @RequestBody ProductItem item){

        CompletableFuture<Cart> cart = service.addItemAsync(id, item);

        CompletableFuture<CartDto> cartDto =  cart.thenApply(
                CartMapper::toCartDto
        );

        return cartDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );

    }

    @PutMapping("/deleteItem/{id}")
    CompletableFuture<ResponseEntity<CartDto>> deleteItem(@PathVariable Long id,
                                                          @Valid @RequestBody ProductItem item){

        CompletableFuture<Cart> cart = service.removeItemAsync(id, item);

        CompletableFuture<CartDto> cartDto =  cart.thenApply(
                CartMapper::toCartDto
        );

        return cartDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @DeleteMapping("/{id}")
    CompletableFuture<ResponseEntity<Cart>> DeleteCart(@Valid @PathVariable Long id){

        service.deleteByIdAsync(id);

        return CompletableFuture.completedFuture(
                ResponseEntity.notFound()
                        .build()
        );
    }

}
