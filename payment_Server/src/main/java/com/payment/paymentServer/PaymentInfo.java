package com.payment.paymentServer;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PaymentInfo {

    @Id
    private Long id;

    private Long balance;
    private String cardNumber;

    private String expiryDate;

    private String cvv; // ⚠️ Consider encrypting or tokenizing this

    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UsedIn> usedIn;

    private boolean isDefault = false;

    public PaymentInfo(Long balance, String cardNumber, String expiryDate, String cvv, CardType cardType, List<UsedIn> usedIn) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
        this.usedIn = usedIn;
    }

    public PaymentInfo() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public List<UsedIn> getUsedIn() {
        return usedIn;
    }

    public void setUsedIn(List<UsedIn> usedIn) {
        this.usedIn = usedIn;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
