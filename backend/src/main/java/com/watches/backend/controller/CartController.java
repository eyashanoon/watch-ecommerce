package com.watches.backend.controller;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.ProductItem;
import com.watches.backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<CartDto> GetCartById(@PathVariable Long id){
        Cart cart = service.findById(id);
        CartDto cartDto =  CartMapper.toCartDto(cart);
        return ResponseEntity.ok().body(cartDto);
    }

    @PostMapping
    ResponseEntity<Cart> CreateCart(@Valid @RequestBody CreateCartDto cartDto){
        Cart cart = service.create(cartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/addItem/{id}")
    ResponseEntity<CartDto> UpdateCart(@Valid @PathVariable Long id, @Valid @RequestBody ProductItem item){
        Cart cart = service.addItem(id, item);
        CartDto cartDto =  CartMapper.toCartDto(cart);
        return ResponseEntity.ok().body(cartDto);
    }

    @PutMapping("/deleteItem/{id}")
    ResponseEntity<CartDto> deleteItem(@PathVariable Long id, @Valid @RequestBody ProductItem item){
        Cart cart = service.removeItem(id, item);
        CartDto cartDto =  CartMapper.toCartDto(cart);
        return ResponseEntity.ok().body(cartDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Cart> DeleteCart(@Valid @PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.notFound().build();
    }

}
