package com.watches.backend.Dto.savedCard;

import com.watches.backend.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSavedCardDTO {
    @NotNull(message = "Card type is required")
    private CardType cardType;
    @NotBlank(message = "Card holder name is required")
    private String cardHolderName;
    @NotBlank(message = "Card number is required")
    private String cardNumber;
    @NotBlank(message = "Card expiration date is required")
    private String expirationDate;
    @NotBlank(message = "Card CVV/CVC code is required")
    private String cvv;
    @NotBlank(message = "Card billing address is required")
    private String billingAddress;
    @NotBlank(message = "Card postal/ZIP code is required")
    private String postalCode;
    private boolean defaultCard = false;
}
