package com.watches.backend.mappers;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.model.Cart;

public class CartMapper {

    public static CartDto toCartDto(Cart cart) {
        return new CartDto(
                cart.getCustomer(),
                cart.getItems());
    }

    public static Cart toCart(CartDto cartDto) {
        return new Cart(
                cartDto.getCustomer(),
                cartDto.getItems()
        );
    }

    public static Cart createToCart(CreateCartDto createCartDto) {
        return new Cart(
                createCartDto.getCustomer(),
                createCartDto.getItems()
        );
    }



}
