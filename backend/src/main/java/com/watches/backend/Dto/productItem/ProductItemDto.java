package com.watches.backend.Dto.productItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDto {
    private Long productId;
    private Integer quantity;
    private Double price;
}
