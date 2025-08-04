package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.WishlistRepository;
import com.watches.backend.exceptions.CustomerNotFoundException;
import com.watches.backend.exceptions.WishlistNotFoundException;
import com.watches.backend.helpers.ProductQueryObject;
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
    private final ProductService productService;


    public WishlistService(WishlistRepository wishlistRepository,
                           CustomerRepository customerRepository,
                           ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
         this.productService = productService;
 
    }

    public CompletableFuture<Wishlist> findByIdAsync(Long id) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() ->
                        new WishlistNotFoundException(id)
                );
        return CompletableFuture.completedFuture(wishlist);
    }

     public CompletableFuture<Wishlist> createAsync(){
        Wishlist wishlist = new Wishlist();
 
        wishlistRepository.save(wishlist);
        return CompletableFuture.completedFuture(wishlist);
    }

     public CompletableFuture<Wishlist> findByCustomerIdAsync(String customerUsername) {

        Customer customer = customerRepository.findByEmail(customerUsername)
                .orElseThrow(() ->
                        new CustomerNotFoundException(customerUsername)
                );
        Wishlist wishlist = customer.getWishlist();
        if(wishlist == null) {
            throw new RuntimeException("Wishlist not found");
        }
        return CompletableFuture.completedFuture(customer.getWishlist());
    }

    public CompletableFuture<Wishlist> addProductAsync(String username, Long productId){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(username);
        return addWishlistCompletableFuture(productId, wishlist);
    }

//    public CompletableFuture<Wishlist> addProductByCustomerIdAsync(@Valid Long customerID, @Valid ProductDto productDto){
//
//        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);
//        return getWishlistCompletableFuture(productDto, wishlist);
//    }

    private CompletableFuture<Wishlist> addWishlistCompletableFuture(Long productId, CompletableFuture<Wishlist> wishlist) {
        Product product = productService.findByIdAsync(productId).join();

        wishlist = wishlist.thenApply(wl -> {
            wl.addItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    private CompletableFuture<Wishlist> removeWishlistCompletableFuture(Long productId, CompletableFuture<Wishlist> wishlist) {
        Product product = productService.findByIdAsync(productId).join();

        wishlist = wishlist.thenApply(wl -> {
            wl.removeItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public CompletableFuture<Wishlist> removeProductAsync(String username, Long productId){
        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(username);
        return removeWishlistCompletableFuture(productId, wishlist);
    }

//    public CompletableFuture<Wishlist> removeProductByCustomerIdAsync(@Valid Long customerID, @Valid ProductDto productDto){
//
//        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);
//
//        return getWishlistCompletableFuture(productDto, wishlist);
//    }

//    public void deleteById(Long id){
//        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id, null);
//
//        wishlist.thenAccept(
//                wishlistRepository::delete
//        );
//    }
 
}
