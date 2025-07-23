package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import com.watches.backend.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> getWishlist(@Valid @PathVariable Long id)
            throws ExecutionException, InterruptedException {

        CompletableFuture<Wishlist> wishlist = service.findById(id);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @GetMapping("/customer/{customerID}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> getWishlistByCustomerId(@Valid @PathVariable Long customerID)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = service.findByCustomerId(customerID);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @PostMapping
    @Async
    CompletableFuture<ResponseEntity<Wishlist>> createWishlist(@Valid @RequestBody CreateWishlistDto wishlistDto)
            throws ExecutionException, InterruptedException {

        CompletableFuture<Wishlist> wishlist = service.create(wishlistDto);
        return CompletableFuture.completedFuture(
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(wishlist.get()));
    }

    @PutMapping("/add/{id}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> addProduct(@Valid @PathVariable Long id,
                                                              @Valid @RequestBody ProductDto product)
            throws ExecutionException, InterruptedException {

        CompletableFuture<Wishlist> wishlist = service.addProduct(id, product);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @PutMapping("/add/customer/{customerID}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> addProductByCustomerId(@Valid @PathVariable Long customerID,
                                                                          @Valid @RequestBody ProductDto product)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = service.addProductByCustomerId(customerID, product);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @PutMapping("/remove/{id}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> removeProduct(@Valid @PathVariable Long id,
                                                                 @Valid @RequestBody ProductDto product)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Wishlist> wishlist = service.removeProduct(id, product);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @PutMapping("/remove/customer/{customerID}")
    @Async
    CompletableFuture<ResponseEntity<WishlistDto>> removeProductByCustomerId(@Valid @PathVariable Long customerID,
                                                                             @Valid @RequestBody ProductDto product)
            throws ExecutionException, InterruptedException {

        CompletableFuture<Wishlist> wishlist = service.removeProductByCustomerId(customerID, product);
        WishlistDto wishlistDto = WishlistMapper.wishlistToDto(wishlist);
        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .body(wishlistDto));
    }

    @DeleteMapping("/{id}")
    @Async
    CompletableFuture<ResponseEntity<Wishlist>> deleteWishlist(@PathVariable Long id)
            throws ExecutionException, InterruptedException {

        service.deleteById(id);
        return CompletableFuture.completedFuture(ResponseEntity.noContent()
                .build());
    }

}
