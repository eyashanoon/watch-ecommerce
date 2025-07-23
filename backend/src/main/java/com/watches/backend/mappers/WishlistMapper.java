package com.watches.backend.mappers;

import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.model.Wishlist;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WishlistMapper {

    public static Wishlist createToWishlist(CreateWishlistDto wishlistDto){
        return new Wishlist(
                wishlistDto.getCustomer(),
                wishlistDto.getProducts()
        );
    }

    public static WishlistDto wishlistToDto(Wishlist wishlist){
        return new WishlistDto(
                wishlist.getCustomer(),
                wishlist.getProducts()
        );
    }

    public static WishlistDto wishlistToDto(CompletableFuture<Wishlist> wishlist) throws ExecutionException, InterruptedException {
        return new WishlistDto(
                wishlist.get().getCustomer(),
                wishlist.get().getProducts()
        );
    }


}
