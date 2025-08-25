package com.watches.backend.Dto.wishlist;

import com.watches.backend.Dto.product.ProductDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishlistDto {
    private String customerName;
    private List<ProductDto> products;
}
