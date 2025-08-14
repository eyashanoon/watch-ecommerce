package com.watches.backend.mappers;

import com.watches.backend.Dto.CartDto.CartDto;
import com.watches.backend.Dto.CartDto.CreateCartDto;
import com.watches.backend.model.Cart;
import com.watches.backend.model.Customer;

public class CartMapper {

    public static CartDto toCartDto(Cart cart) {
        return new CartDto(
                cart.getCustomer().getEmail(),
                cart.getItems().stream().map(ProductItemMapper::toProductItemDto).toList()
        );
    }

//    public static Cart toCart(CartDto cartDto) {
//        return new Cart(
//                cartDto.getCustomer(),
//                cartDto.getItems()
//        );
//    }
//
//    public static Cart createToCart(CreateCartDto createCartDto, Customer customer) {
//        return new Cart(
//                customer,
//                createCartDto.getItems()
//        );
//    }



}
