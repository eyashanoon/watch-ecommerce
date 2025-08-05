package com.watches.backend.controller;

import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import com.watches.backend.service.WishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/wishlist")
@AllArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @GetMapping("/customer/{customerUsername}")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto getWishlistByCustomerUsername(@Valid @PathVariable String customerUsername, @ModelAttribute ProductQueryObject productQueryObject) {

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(customerUsername, productQueryObject);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }

    @GetMapping("/me")
    WishlistDto getMyWishlist(@ModelAttribute ProductQueryObject query) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CompletableFuture<Wishlist> wishlist = service.findByCustomerIdAsync(username, query);
        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );
        return wishlistDto.join();
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto addProduct(@Valid @RequestParam("item") List<Long> item){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        CompletableFuture<Wishlist> wishlist = service.addProductAsync(username, item);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }

    @PutMapping("/remove")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    WishlistDto removeProduct(@Valid @RequestParam("item") List<Long> item){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        CompletableFuture<Wishlist> wishlist = service.removeProductAsync(username, item);

        CompletableFuture<WishlistDto> wishlistDto = wishlist.thenApply(
                WishlistMapper::wishlistToDto
        );

        return wishlistDto.join();
    }
}
