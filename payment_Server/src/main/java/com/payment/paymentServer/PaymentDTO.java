package com.payment.paymentServer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.List;

public class PaymentDTO {
     private String cardNumber;
     private Long amount;

    private String expiryDate;

    private String cvv; // ⚠️ Consider encrypting or tokenizing this

     private CardType cardType;
     private Long userId;
     private String companyName;

     public PaymentDTO(Long balance, String cardNumber, Long amount, String expiryDate, String cvv) {
          this.cardNumber = cardNumber;
         this.amount = amount;
         this.expiryDate = expiryDate;
         this.cvv = cvv;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
