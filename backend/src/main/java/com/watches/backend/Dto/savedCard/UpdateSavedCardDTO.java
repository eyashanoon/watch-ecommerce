package com.watches.backend.Dto.savedCard;

import com.watches.backend.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSavedCardDTO {
    private CardType cardType;
    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String billingAddress;
    private String postalCode;
    private boolean defaultCard = false;
}
