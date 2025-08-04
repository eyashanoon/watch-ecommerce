package com.watches.backend.controller;

import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import com.watches.backend.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("/customer/{customerUsername}")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto getWishlistByCustomerUsername(@Valid @PathVariable String customerUsername) {

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(customerUsername);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    WishlistDto getMyWishlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(username);
        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );
        return wishlistDto.join();
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto addProduct(@Valid @RequestParam("productId") Long productId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        CompletableFuture<Wishlist> wishlist = service.addProductAsync(username, productId);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();

    }

    @PutMapping("/remove")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto removeProduct(@Valid @RequestParam Long productId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        CompletableFuture<Wishlist> wishlist = service.removeProductAsync(username, productId);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }
}
