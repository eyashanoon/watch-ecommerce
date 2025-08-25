package com.watches.backend.mappers;

import com.watches.backend.Dto.wishlist.WishlistDto;
import com.watches.backend.model.Wishlist;

public class WishlistMapper {
    public static WishlistDto wishlistToDto(Wishlist wishlist) {
        return new WishlistDto(
                wishlist.getCustomer().getUsername(),
                wishlist.getProducts().stream().map(ProductMapper::toDto).toList()
        );
    }
}
