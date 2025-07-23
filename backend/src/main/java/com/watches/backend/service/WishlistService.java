package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.WishlistRepository;
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
import java.util.concurrent.ExecutionException;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    public WishlistService(WishlistRepository wishlistRepository, CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    @Async
    public CompletableFuture<Wishlist> findById(Long id){
        return CompletableFuture.completedFuture(wishlistRepository.findById(id)
                .orElseThrow(() -> new WishlistNotFoundException(id)));
    }

    @Async
    public CompletableFuture<Wishlist> create(CreateWishlistDto wishlistDto){
        Wishlist wishlist = WishlistMapper.createToWishlist(wishlistDto);
        wishlistRepository.save(wishlist);
        return CompletableFuture.completedFuture(wishlist);
    }

    @Async
    public CompletableFuture<Wishlist> findByCustomerId(@Valid Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->  new RuntimeException("Customer not found with id " + customerId));
        return CompletableFuture.completedFuture(customer.getWishlist());
    }

    @Async
    public CompletableFuture<Wishlist> addProduct(Long id, ProductDto productDto)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = this.findById(id);
        Product product = ProductMapper.DtoToProduct(productDto);
        wishlist.get().addItem(product);
        wishlistRepository.save(wishlist.get());
        return CompletableFuture.completedFuture(wishlist.get());
    }

    @Async
    public CompletableFuture<Wishlist> addProductByCustomerId(@Valid Long customerID, @Valid ProductDto productDto)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = this.findByCustomerId(customerID);
        Product product = ProductMapper.DtoToProduct(productDto);
        wishlist.get().addItem(product);
        wishlistRepository.save(wishlist.get());
        return CompletableFuture.completedFuture(wishlist.get());
    }

    @Async
    public CompletableFuture<Wishlist> removeProduct(@Valid Long id, @Valid ProductDto productDto)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = this.findById(id);
        Product product = ProductMapper.DtoToProduct(productDto);
        wishlist.get().removeItem(product);
        wishlistRepository.save(wishlist.get());
        return CompletableFuture.completedFuture(wishlist.get());
    }

    @Async
    public CompletableFuture<Wishlist> removeProductByCustomerId(@Valid Long customerID, @Valid ProductDto productDto)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = this.findByCustomerId(customerID);
        Product product = ProductMapper.DtoToProduct(productDto);
        wishlist.get().removeItem(product);
        wishlistRepository.save(wishlist.get());
        return CompletableFuture.completedFuture(wishlist.get());
    }

    @Async
    public CompletableFuture<Wishlist> deleteById(Long id) throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = this.findById(id);
        wishlistRepository.delete(wishlist.get());
        return CompletableFuture.completedFuture(wishlist.get());
    }
}
