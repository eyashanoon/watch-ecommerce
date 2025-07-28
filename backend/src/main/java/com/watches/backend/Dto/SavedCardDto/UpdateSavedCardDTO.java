package com.watches.backend.Dto.SavedCardDto;

import com.watches.backend.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateSavedCardDTO {
    @NotNull
    private Long id;
    @NotBlank
     @NotBlank
    private String cardHolderName;
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String expiryDate;
    @NotNull
    private CardType cardType;
    private boolean isDefault = false;

    public UpdateSavedCardDTO(Long id , String cardHolderName, String cardNumber, CardType cardType, boolean isDefault) {
        this.id = id;
         this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.isDefault = isDefault;
    }
    public UpdateSavedCardDTO() {}


    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
