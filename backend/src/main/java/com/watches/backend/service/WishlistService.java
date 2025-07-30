package com.watches.backend.service;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.ProductDto.WishlistProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.WishlistRepository;
import com.watches.backend.exceptions.CustomerNotFoundException;
import com.watches.backend.exceptions.WishlistNotFoundException;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.helpers.factories.ProductFilterFactory;
import com.watches.backend.helpers.productOptions.IProductFilter;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import com.watches.backend.model.Wishlist;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    private final ImageService imageService;

    public WishlistService(WishlistRepository wishlistRepository,
                           CustomerRepository customerRepository,
                           ImageService imageService) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
        this.imageService = imageService;
 
    public CompletableFuture<Wishlist> findByIdAsync(Long id, ProductQueryObject queryObject) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() ->
                        new WishlistNotFoundException(id)
                );
        List<IProductFilter> filters = ProductFilterFactory.getFilters(queryObject);

        for(IProductFilter filter : filters) {
            filter.applyFilter(wishlist.getProducts().stream(), queryObject);
        }
        return CompletableFuture.completedFuture(wishlist);
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

    public CompletableFuture<Wishlist> addProductAsync(Long id, WishlistProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id, null);
        return getWishlistCompletableFuture(productDto, wishlist);
    }

    public CompletableFuture<Wishlist> addProductByCustomerIdAsync(@Valid Long customerID, @Valid WishlistProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);
        return getWishlistCompletableFuture(productDto, wishlist);
    }

    private CompletableFuture<Wishlist> getWishlistCompletableFuture(@Valid WishlistProductDto productDto, CompletableFuture<Wishlist> wishlist) {
        Product product = ProductMapper.WishlistDtoToProduct(productDto);
        CompletableFuture<Image> image = imageService.getImageById(productDto.getImageId());
        image.thenAccept(product::setImage);

        wishlist = wishlist.thenApply(wl -> {
            wl.addItem(product);
            return wishlistRepository.save(wl);
        });

        return wishlist.thenApply(wl -> wl);
    }

    public CompletableFuture<Wishlist> removeProductAsync(@Valid Long id, @Valid WishlistProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id, null);
        return getWishlistCompletableFuture(productDto, wishlist);
    }

    public CompletableFuture<Wishlist> removeProductByCustomerIdAsync(@Valid Long customerID, @Valid WishlistProductDto productDto){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(customerID);

        return getWishlistCompletableFuture(productDto, wishlist);
    }

    public void deleteById(Long id){
        CompletableFuture<Wishlist> wishlist = this.findByIdAsync(id, null);

        wishlist.thenAccept(
                wishlistRepository::delete
        );
    }
}
