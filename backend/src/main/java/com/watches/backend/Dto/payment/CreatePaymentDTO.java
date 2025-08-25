package com.watches.backend.Dto.payment;

import com.watches.backend.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentDTO {
    private PaymentMethod paymentMethod;
}
