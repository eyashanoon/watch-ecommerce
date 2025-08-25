package com.watches.backend.Dto.wishlist;

import com.watches.backend.model.Customer;
import com.watches.backend.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWishlistDto {
    @NotBlank(message = "wishlist Owner is required")
    private Customer customer;
    @Size(min = 1, message = "Wishlist must contain at least 1 item")
    private List<Product> products;
}
