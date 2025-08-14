package com.watches.backend.Dto.WishlistDto;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
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
