package com.watches.backend.model;

import jakarta.persistence.*;
import com.watches.backend.enums.CardType;

@Entity
@Table(name = "saved_cards")
public class SavedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolderName;

    private String cardNumber;

    private String expiryDate;

    private String cvv; // ⚠️ Consider encrypting or tokenizing this

    @Enumerated(EnumType.STRING)
    private CardType cardType; // VISA, MASTERCARD, etc.

    private boolean isDefault = false;

    @OneToOne(mappedBy = "savedCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    // Constructors
    public SavedCard() {}

    public SavedCard(String cardHolderName, String cardNumber, String expiryDate,
                     String cvv, CardType cardType, Customer customer) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
        this.customer = customer;
    }

    // Getters and Setters

    public Long getId() {
        return id;
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
