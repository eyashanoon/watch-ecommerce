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

    private String expirationDate;

    private String cvv; // ⚠️ Consider encrypting or tokenizing this
    private String billingAddress;
    private String postalCode;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    private List<UsedIn> usedIn;

    private boolean isDefault = false;

    public PaymentInfo(Long id,
                       Double balance,
                       String cardNumber,
                       String expirationDate,
                       String cvv,
                       String billingAddress,
                       String postalCode,
                       CardType cardType,
                       List<UsedIn> usedIn,
                       boolean isDefault) {
        this.id = id;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.postalCode = postalCode;
        this.cardType = cardType;
        this.usedIn = usedIn;
        this.isDefault = isDefault;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "id=" + id +
                ", balance=" + balance +
                ", cardNumber" + cardNumber +
                ", expiryDate='" + expirationDate + '\'' +
                ", cvv='***'" +
                ", cardType=" + cardType +
                ", usedIn=" + (usedIn != null ? usedIn.size() + " records" : "null") +
                ", isDefault=" + isDefault +
                '}';
    }

}
