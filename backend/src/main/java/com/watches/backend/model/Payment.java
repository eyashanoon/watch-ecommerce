package com.watches.backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import  com.watches.backend.enums.PaymentStatus;
import  com.watches.backend.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public Payment(Double amount, PaymentMethod method) {
        this.amount = amount;
        this.method = method;
         this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

}
