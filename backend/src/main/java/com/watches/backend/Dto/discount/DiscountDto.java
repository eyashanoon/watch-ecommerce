package com.watches.backend.Dto.discount;


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
public class DiscountDto {
    private Long discountId;
    private List<Long> products;
    private Double percentage;
    private LocalDateTime ExpiresAt;
    private boolean Expired;
}
