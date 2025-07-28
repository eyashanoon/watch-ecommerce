package com.watches.backend.Dto.SavedCardDto;

import com.watches.backend.enums.CardType;

public class SavedCardDTO {
    private Long id;
     private String cardHolderName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private CardType cardType;
    private boolean isDefault = false;
    private Long customerID;


    public SavedCardDTO() {}
    public SavedCardDTO(Long id, String cardHolderName, String cardNumber, CardType cardType,String cvv, boolean isDefault, Long customerID) {
        this.id = id;
         this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cvv = cvv;
        this.isDefault = isDefault;
        this.customerID = customerID;
    }


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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }
}
