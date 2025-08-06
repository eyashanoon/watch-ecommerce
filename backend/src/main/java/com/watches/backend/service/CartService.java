package com.watches.backend.service;

import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.Dto.productItemDto.CreateProductItemDto;
import com.watches.backend.Repositories.CartRepository;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.exceptions.CartNotFoundException;
import com.watches.backend.exceptions.CustomerNotFoundException;
import com.watches.backend.exceptions.ProductNotFoundException;
import com.watches.backend.mappers.CartMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import com.watches.backend.model.ProductItem;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;

    public CompletableFuture<Cart> createAsync(){
        Cart cart = new Cart();
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

    public CompletableFuture<Cart> addItemAsync(String username, List<CreateProductItemDto> items){
        Cart cart = findByCustomerUsername(username).join();

        for(CreateProductItemDto productItem : items){
            Product product = productService.findByIdAsync(productItem.getProductId()).join();
            cart.addItem(new ProductItem(product, productItem.getQuantity()));
        }
        repository.save(cart);
        return CompletableFuture.completedFuture(cart);
    }

    public CompletableFuture<Cart> removeItemAsync(String username, List<CreateProductItemDto> items){
        Cart cart = findByCustomerUsername(username).join();

        for(CreateProductItemDto productItem : items){
            Product product = productService.findByIdAsync(productItem.getProductId()).join();
            cart.removeItem(new ProductItem(product, productItem.getQuantity()));
        }

        repository.save(cart);

        return CompletableFuture.completedFuture(cart);
    }


    public CompletableFuture<Cart> findByCustomerUsername(String customerUsername) {
        Customer customer = customerRepository.findByEmail(customerUsername)
                .orElseThrow(() -> new CustomerNotFoundException(customerUsername));
        return CompletableFuture.completedFuture(customer.getCart());
    }

    public void delete(Cart cart) {
        repository.delete(cart);
    }
}
