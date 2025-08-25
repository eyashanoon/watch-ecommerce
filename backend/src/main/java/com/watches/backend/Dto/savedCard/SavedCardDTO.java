package com.watches.backend.Dto.savedCard;

import com.watches.backend.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedCardDTO {
    private Long id;
     private String cardHolderName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private CardType cardType;
    private boolean isDefault = false;
    private Long customerID;
}
