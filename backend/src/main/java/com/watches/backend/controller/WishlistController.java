package com.watches.backend.controller;

import com.watches.backend.Dto.wishlist.WishlistDto;
import com.watches.backend.helpers.query.ProductQueryObject;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import com.watches.backend.service.AuthService;
import com.watches.backend.service.WishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/wishlist")
@AllArgsConstructor
public class WishlistController {

    private final WishlistService service;
    private final AuthService authService;
    @GetMapping("/customer/{customerUsername}")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('SEE_WISHLIST')")
    WishlistDto getWishlistByCustomerUsername(@Valid @PathVariable String customerUsername,
                                              @ModelAttribute ProductQueryObject productQueryObject) {

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(customerUsername, productQueryObject);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }

    @GetMapping("/me")
    WishlistDto getMyWishlist(@ModelAttribute ProductQueryObject query) {
        String username = authService.getCurrentUserName();

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(username, query);
        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );
        return wishlistDto.join();
    }

    @PutMapping("/add")
    WishlistDto addProduct(@Valid @RequestParam("item") List<Long> item){
        String username = authService.getCurrentUserName();

        CompletableFuture<Wishlist> wishlist = service.addProductAsync(username, item);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }

    @PutMapping("/remove")
    WishlistDto removeProduct(@Valid @RequestParam("item") List<Long> item){
        String username = authService.getCurrentUserName();

        CompletableFuture<Wishlist> wishlist = service.removeProductAsync(username, item);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }
}
