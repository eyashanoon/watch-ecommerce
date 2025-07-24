package com.watches.backend.mappers;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.model.Discount;

public class DiscountMapper {

    public static Discount createToDiscount(CreateDiscountDto discountDto){
        return new Discount(
                discountDto.getProduct(),
                discountDto.getDiscountPercentage(),
                discountDto.getExpireAt()
        );
    }

}
