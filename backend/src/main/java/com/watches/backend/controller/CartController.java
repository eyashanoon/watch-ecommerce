package com.watches.backend.controller;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.Repositories.CartRepository;
import com.watches.backend.exceptions.CartNotFoundException;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.ProductItem;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartRepository repository;

    public CartController(CartRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/carts/{id}")
    CartDto GetCartById(@PathVariable Long id){
        return repository.findById(id)
                .map(CartMapper::toCartDto)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @PostMapping("/carts")
    Cart CreateCart(@Valid @RequestBody CreateCartDto cartDto){
        return repository.save(CartMapper.createToCart(cartDto));
    }

    @PutMapping("/carts/addItem/{id}")
    void UpdateCart(@PathVariable Long id, @RequestBody List<ProductItem> itemslist){
        repository.findById(id)
                .map(c -> {
                    c.getItems().addAll(itemslist);
                    return repository.save(c);
                });
    }

    @PutMapping("/carts/deleteItem/{id}")
    void deleteItem(@PathVariable Long id){
        repository.findById(id)
                .map(c -> {
                    c.getItems().removeAll(c.getItems());
                    return repository.save(c);
                });
    }

    @DeleteMapping("/carts/{id}")
    void DeleteCart(@PathVariable Long id){
        repository.deleteById(id);
    }

}
