package com.watches.backend.service;

import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.UserRepository;

import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.Repositories.WishlistRepository;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.helpers.query.ProductQueryObject;
import com.watches.backend.helpers.specification.SpecificationBuilder;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import com.watches.backend.model.Wishlist;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Transactional
    public CompletableFuture<Wishlist> createAsync(){
        Wishlist wishlist = new Wishlist();
        return CompletableFuture.completedFuture(wishlistRepository.save(wishlist));
    }

     public CompletableFuture<Wishlist> findByCustomerIdAsync(String customerUsername, ProductQueryObject queryObject) {

        Customer customer = customerRepository.findByEmail(customerUsername)
                .orElseThrow(() -> CException.notFound(Customer.class, "email", customerUsername));
        Wishlist wishlist = customer.getWishlist();

        if(wishlist == null) {
            throw CException.notFound(Wishlist.class, "Customer email", customerUsername);
        }

        if(queryObject == null) {
            return CompletableFuture.completedFuture(wishlist);
        }

        List<Long> productIds = wishlist.getProducts()
                .stream()
                .map(Product::getId)
                .toList();

        Specification<Product> spec = new SpecificationBuilder<>(Product.class)
                .withFilter(queryObject)
                .build();

        Specification<Product> wishlistProds = (root, query, cb) -> root.get("id").in(productIds);

        Specification<Product> finalSpec = wishlistProds.and(spec);

        Page<Product> res = productRepository.findAll(finalSpec,
                PageRequest.of(queryObject.getPage() - 1, queryObject.getPageSize()));

        Wishlist filtered = new Wishlist();
        filtered.setProducts(res.getContent());
        filtered.setId(wishlist.getId());
        filtered.setCustomer(wishlist.getCustomer());

        return CompletableFuture.completedFuture(filtered);
    }

    public CompletableFuture<Wishlist> addProductAsync(String username, List<Long> productId){

        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(username, null);
        return addWishlistCompletableFuture(productId, wishlist);
    }

    private CompletableFuture<Wishlist> addWishlistCompletableFuture(List<Long> productId, CompletableFuture<Wishlist> wishlist) {
        Wishlist wl = wishlist.join();
        for(Long id : productId){
            Product product = productService.findByIdAsync(id).join();
            wl.addItem(product);
        }
        wishlistRepository.save(wl);
        return CompletableFuture.completedFuture(wl);
    }

    private CompletableFuture<Wishlist> removeWishlistCompletableFuture(List<Long> productId, CompletableFuture<Wishlist> wishlist) {
        Wishlist wl = wishlist.join();
        for(Long id : productId){
            Product product = productService.findByIdAsync(id).join();
            wl.removeItem(product);
        }

        wishlistRepository.save(wl);
        return CompletableFuture.completedFuture(wl);
    }

    public CompletableFuture<Wishlist> removeProductAsync(String username, List<Long> productId){
        CompletableFuture<Wishlist> wishlist = this.findByCustomerIdAsync(username, null);
        return removeWishlistCompletableFuture(productId, wishlist);
    }

    public void delete(Wishlist wishlist) {
        wishlist.setDeleted(true);
        wishlistRepository.save(wishlist);
    }

}
