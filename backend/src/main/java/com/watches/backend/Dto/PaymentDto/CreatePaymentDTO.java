package com.watches.backend.Dto.PaymentDto;

import com.watches.backend.enums.PaymentMethod;

public class CreatePaymentDTO {
    private PaymentMethod paymentMethod;

    public CreatePaymentDTO(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
