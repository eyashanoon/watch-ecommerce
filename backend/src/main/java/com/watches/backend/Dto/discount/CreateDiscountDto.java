package com.watches.backend.Dto.discount;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiscountDto {
    @NotNull(message = "Discount must belong to at least 1 product")
    List<Long> product = null;
    @Future(message = "Discount must expire at the future")
    LocalDateTime ExpireAt = null;
    @Positive(message = "Discount percentage must be positive")
    Double DiscountPercentage = 0.0;
}
