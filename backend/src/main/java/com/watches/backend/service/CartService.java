package com.watches.backend.service;

import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.Repositories.CartRepository;
import com.watches.backend.exceptions.CartNotFoundException;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.ProductItem;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository repository;
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public Cart create(CreateCartDto cartDto){
        Cart cart = CartMapper.createToCart(cartDto);
        repository.save(cart);
        return cart;
    }

    public Cart findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    public void delete(Cart cart){
        repository.delete(cart);
    }

    public void deleteById(Long id){
        Cart cart =  findById(id);
        this.delete(cart);
    }

    public Cart addItem(Long id, ProductItem item){
        Cart cart = findById(id);
        cart.addItem(item);
        repository.save(cart);
        return cart;
    }

    public Cart removeItem(Long id, ProductItem item){
        Cart cart = findById(id);
        cart.removeItem(item);
        repository.save(cart);
        return cart;
    }


}
