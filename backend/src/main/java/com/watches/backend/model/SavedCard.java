package com.watches.backend.model;

import com.watches.backend.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "saved_cards")
public class SavedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String cvv;
    @Column(nullable = false)
    private String billingAddress;
    @Column(nullable = false)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    private boolean defaultCard = false;

    @OneToOne(mappedBy = "savedCard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Customer customer;

    public SavedCard(String cardHolderName,
                     String cardNumber,
                     String expirationDate,
                     String cvv,
                     String billingAddress,
                     String postalCode,
                     CardType cardType,
                     boolean defaultCard,
                     Customer customer) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.postalCode = postalCode;
        this.cardType = cardType;
        this.defaultCard = defaultCard;
        this.customer = customer;
    }
}
