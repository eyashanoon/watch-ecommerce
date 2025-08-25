package com.watches.backend.controller;

import com.watches.backend.Dto.cart.CartDto;
import com.watches.backend.Dto.productItem.CreateProductItemDto;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.service.AuthService;
import com.watches.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {
 
    private final CartService service;
    private final AuthService authService;

    @GetMapping("/{customerUsername}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CART')")
    CartDto getCart(@Valid @PathVariable String customerUsername){
        CompletableFuture<Cart> cart = service.findByCustomerUsername(customerUsername);
        return CartMapper.toCartDto(cart.join());
    }

    @GetMapping("/me")
    CartDto getMe(){
        String username = authService.getCurrentUserName();

        CompletableFuture<Cart> cart = service.findByCustomerUsername(username);
        return CartMapper.toCartDto(cart.join());
    }

    @PutMapping("/add")
    CartDto UpdateCart(@Valid @RequestBody List<CreateProductItemDto> item){
        String username = authService.getCurrentUserName();

        CompletableFuture<Cart> cart = service.addItemAsync(username, item);

        return CartMapper.toCartDto(cart.join());
    }

    @PutMapping("/remove")
    CartDto deleteItem(@Valid @RequestBody List<CreateProductItemDto> item){
        String username = authService.getCurrentUserName();

        CompletableFuture<Cart> cart = service.removeItemAsync(username, item);

        return CartMapper.toCartDto(cart.join());
    }

}
