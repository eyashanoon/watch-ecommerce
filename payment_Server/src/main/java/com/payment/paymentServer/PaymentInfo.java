package com.payment.paymentServer;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;
    private String cardNumber;

    private String expiryDate;

    private String cvv; // ⚠️ Consider encrypting or tokenizing this

    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    private List<UsedIn> usedIn;

    private boolean isDefault = false;

    public PaymentInfo(Double balance, String cardNumber, String expiryDate, String cvv, CardType cardType, List<UsedIn> usedIn) {
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    @Override
    public String toString() {
        return "PaymentInfo{" +
                "id=" + id +
                ", balance=" + balance +
                ", cardNumber" + cardNumber +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='***'" +
                ", cardType=" + cardType +
                ", usedIn=" + (usedIn != null ? usedIn.size() + " records" : "null") +
                ", isDefault=" + isDefault +
                '}';
    }

}
