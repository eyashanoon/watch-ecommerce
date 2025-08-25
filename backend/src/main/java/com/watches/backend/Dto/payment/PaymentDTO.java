package com.watches.backend.Dto.payment;

import com.watches.backend.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String cardNumber;
    private Double amount;
    private String expirationDate;
    private String cvv;
    private String billingAddress;
    private String postalCode;
    private CardType cardType;
    private Long userId;
    private String companyName;
}
