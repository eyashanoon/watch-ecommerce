package com.watches.backend.Dto.productItemDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductItemDto {
    private Long productId;
    private Integer quantity;
}
