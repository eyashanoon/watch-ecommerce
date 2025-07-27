package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.WishlistRepository;
import com.watches.backend.exceptions.CustomerNotFoundException;
import com.watches.backend.exceptions.WishlistNotFoundException;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import com.watches.backend.model.Wishlist;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    public WishlistService(WishlistRepository wishlistRepository, CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    public CompletableFuture<Wishlist> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(
                wishlistRepository.findById(id)
                .orElseThrow(() ->
                        new WishlistNotFoundException(id)
                )
        );
    }

    public CompletableFuture<Wishlist> createAsync(CreateWishlistDto wishlistDto){
        Wishlist wishlist = WishlistMapper.createToWishlist(wishlistDto);
        wishlistRepository.save(wishlist);
        return CompletableFuture.completedFuture(wishlist);
    }

    public CompletableFuture<Wishlist> findByCustomerIdAsync(@Valid Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(customerId)
                );

        return CompletableFuture.completedFuture(customer.getWishlist());
    }

    public CompletableFuture<Wishlist> addProductAsync(Long id, ProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id);
        Product product = ProductMapper.DtoToProduct(productDto);

        wishlist = wishlist.thenApply(wl -> {
            wl.addItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public CompletableFuture<Wishlist> addProductByCustomerIdAsync(@Valid Long customerID, @Valid ProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);
        Product product = ProductMapper.DtoToProduct(productDto);

        wishlist = wishlist.thenApply(wl -> {
            wl.addItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public CompletableFuture<Wishlist> removeProductAsync(@Valid Long id, @Valid ProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id);
        Product product = ProductMapper.DtoToProduct(productDto);

        wishlist = wishlist.thenApply(wl -> {
            wl.removeItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public CompletableFuture<Wishlist> removeProductByCustomerIdAsync(@Valid Long customerID, @Valid ProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);
        Product product = ProductMapper.DtoToProduct(productDto);

        wishlist = wishlist.thenApply(wl -> {
            wl.removeItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public void deleteById(Long id){
        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id);

        wishlist.thenAccept(
                wishlistRepository::delete
        );
    }
}
