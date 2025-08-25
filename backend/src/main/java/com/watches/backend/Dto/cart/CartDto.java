package com.watches.backend.Dto.cart;

import com.watches.backend.Dto.productItem.ProductItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private String customerUsername;
    private List<ProductItemDto> items;
}
