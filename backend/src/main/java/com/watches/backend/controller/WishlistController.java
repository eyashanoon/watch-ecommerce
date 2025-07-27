package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import com.watches.backend.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/wishlist")
@Async
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    CompletableFuture<ResponseEntity<WishlistDto>> getWishlist(@Valid @PathVariable Long id, @ModelAttribute ProductQueryObject queryObject) {

        CompletableFuture<Wishlist> wishlist = service.findByIdAsync(id, queryObject);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );

    }

    @GetMapping("/customer/{customerID}")
    CompletableFuture<ResponseEntity<WishlistDto>> getWishlistByCustomerId(@Valid @PathVariable Long customerID){

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(customerID);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @PostMapping
    CompletableFuture<ResponseEntity<Wishlist>> createWishlist(@Valid @RequestBody CreateWishlistDto wishlistDto){

        CompletableFuture<Wishlist> wishlist = service.createAsync(wishlistDto);

        return wishlist.thenApply(wl ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(wl)
        );
    }

    @PutMapping("/add/{id}")
    CompletableFuture<ResponseEntity<WishlistDto>> addProduct(@Valid @PathVariable Long id,
                                                              @Valid @RequestBody ProductDto product){

        CompletableFuture<Wishlist> wishlist = service.addProductAsync(id, product);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @PutMapping("/add/customer/{customerID}")
    CompletableFuture<ResponseEntity<WishlistDto>> addProductByCustomerId(@Valid @PathVariable Long customerID,
                                                                          @Valid @RequestBody ProductDto product){

        CompletableFuture<Wishlist> wishlist = service.addProductByCustomerIdAsync(customerID, product);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @PutMapping("/remove/{id}")
    CompletableFuture<ResponseEntity<WishlistDto>> removeProduct(@Valid @PathVariable Long id,
                                                                 @Valid @RequestBody ProductDto product){

        CompletableFuture<Wishlist> wishlist = service.removeProductAsync(id, product);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @PutMapping("/remove/customer/{customerID}")
    CompletableFuture<ResponseEntity<WishlistDto>> removeProductByCustomerId(@Valid @PathVariable Long customerID,
                                                                             @Valid @RequestBody ProductDto product){

        CompletableFuture<Wishlist> wishlist = service.removeProductByCustomerIdAsync(customerID, product);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.thenApply(dto ->
                ResponseEntity.ok()
                        .body(dto)
        );
    }

    @DeleteMapping("/{id}")
    CompletableFuture<ResponseEntity<Wishlist>> deleteWishlist(@PathVariable Long id){

        service.deleteById(id);

        return CompletableFuture.completedFuture(ResponseEntity.noContent()
                .build()
        );
    }

}
