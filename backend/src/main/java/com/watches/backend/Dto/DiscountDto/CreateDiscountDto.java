package com.watches.backend.Dto.DiscountDto;

import com.watches.backend.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class CreateDiscountDto {
    @NotBlank(message = "Discount Product Required")
    Product product = null;

    @NotBlank(message = "Discount must Expire in the future")
    LocalDateTime ExpireAt = null;

    @Positive(message = "Discount percentage must be positive")
    Double DiscountPercentage = 0.0;

    public CreateDiscountDto() {}

    public CreateDiscountDto(Product product, LocalDateTime expireAt, Double discountPercentage) {
        this.product = product;
        ExpireAt = expireAt;
        DiscountPercentage = discountPercentage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getExpireAt() {
        return ExpireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        ExpireAt = expireAt;
    }

    public Double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }
}
