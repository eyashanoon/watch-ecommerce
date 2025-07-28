package com.watches.backend.service;

import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.Repositories.CartRepository;
import com.watches.backend.exceptions.CartNotFoundException;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.ProductItem;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class CartService {

    private final CartRepository repository;
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Cart> createAsync(CreateCartDto cartDto){
        Cart cart = CartMapper.createToCart(cartDto);
        repository.save(cart);
        return CompletableFuture.completedFuture(cart);
    }

    public CompletableFuture<Cart> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(repository.findById(id)
                .orElseThrow(() ->
                        new CartNotFoundException(id)
                )
        );
    }

    public void deleteByIdAsync(Long id){
        CompletableFuture<Cart> cart =  findByIdAsync(id);

        cart.thenAccept(
                repository::delete
        );

    }

    public CompletableFuture<Cart> addItemAsync(Long id, ProductItem item){
        CompletableFuture<Cart> cart = findByIdAsync(id);

        cart.thenAccept(c -> {
            c.addItem(item);
            repository.save(c);
        });

        return cart.thenApply(c -> c);
    }

    public CompletableFuture<Cart> removeItemAsync(Long id, ProductItem item){
        CompletableFuture<Cart> cart = findByIdAsync(id);

        cart.thenAccept(c -> {
            c.removeItem(item);
            repository.save(c);
        });

        return cart.thenApply(c -> c);
    }


}
