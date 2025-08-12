package com.watches.backend.controller;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.productItemDto.CreateProductItemDto;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CART')")
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
    CartDto UpdateCart(@Valid @RequestBody List<CreateProductItemDto> item){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Cart> cart = service.addItemAsync(username, item);

        return CartMapper.toCartDto(cart.join());
    }

    @PutMapping("/remove")
    CartDto deleteItem(@Valid @RequestBody List<CreateProductItemDto> item){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Cart> cart = service.removeItemAsync(username, item);

        return CartMapper.toCartDto(cart.join());
    }

}
