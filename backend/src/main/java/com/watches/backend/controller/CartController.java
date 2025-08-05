package com.watches.backend.controller;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.Dto.productItemDto.CreateProductItemDto;
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
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {
 
    private final CartService service;

    @GetMapping("/{customerUsername}")
    CartDto getCart(@Valid @PathVariable String customerUsername){
        CompletableFuture<Cart> cart = service.findByCustomerUsername(customerUsername);
        return CartMapper.toCartDto(cart.join());
    }

    @GetMapping("/me")
    CartDto getMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Cart> cart = service.findByCustomerUsername(username);
        return CartMapper.toCartDto(cart.join());
    }

    @PutMapping("/add")
    CompletableFuture<ResponseEntity<CartDto>> UpdateCart(@Valid @RequestBody List<CreateProductItemDto> item){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Cart> cart = service.addItemAsync(username, item);

        CompletableFuture<CartDto> cartDto =  cart.thenApply(
                CartMapper::toCartDto
        );

        return cartDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );

    }

    @PutMapping("/remove")
    CompletableFuture<ResponseEntity<CartDto>> deleteItem(@Valid @RequestBody List<CreateProductItemDto> item){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Cart> cart = service.removeItemAsync(username, item);

        CompletableFuture<CartDto> cartDto =  cart.thenApply(
                CartMapper::toCartDto
        );

        return cartDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

}
