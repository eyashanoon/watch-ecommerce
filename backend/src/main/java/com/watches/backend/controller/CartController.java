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
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository  productRepository;

    public CartController(CartRepository repository, CustomerRepository customerRepository,ProductRepository  productRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;

    }

    @GetMapping("/carts/{id}")
    CartDto GetCartById(@PathVariable Long id){
        return repository.findById(id)
                .map(CartMapper::toCartDto)
                .orElseThrow(() -> new CartNotFoundException(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/carts")
    Cart CreateCart(@Valid @RequestBody CreateCartDto cartDto){
        Customer customer=customerRepository.findById(cartDto.getCustomer()).orElseThrow(()->new CustomerNotFoundException(cartDto.getCustomer()));
         cartDto.getItems().forEach(productItem -> {
            productItem.setProduct(productRepository.findById(productItem.getProduct().getId()).orElseThrow(()->new ProductNotFoundException(productItem.getProduct().getId())));
        });

        return repository.save(CartMapper.createToCart(cartDto,customer));


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
