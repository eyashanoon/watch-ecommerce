package com.watches.backend.Dto.SavedCardDto;

import com.watches.backend.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
