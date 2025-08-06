package com.watches.backend.mappers;

import com.watches.backend.Dto.DiscountDto.DiscountDto;
import com.watches.backend.model.Discount;


public class DiscountMapper {

    public static DiscountDto toDiscountDto(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getProductsId(),
                discount.getDiscount(),
                discount.getEndDate(),
                discount.isExpired()
        );
    }


}
