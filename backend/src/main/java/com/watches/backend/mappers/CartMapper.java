package com.watches.backend.mappers;

import com.watches.backend.Dto.cart.CartDto;
import com.watches.backend.model.Cart;

public class CartMapper {

    public static CartDto toCartDto(Cart cart) {
        return new CartDto(
                cart.getCustomer().getEmail(),
                cart.getItems().stream().map(ProductItemMapper::toProductItemDto).toList()
        );
    }
}
