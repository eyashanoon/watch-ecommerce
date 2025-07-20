package com.watches.backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import  com.watches.backend.enums.PaymentStatus;
import  com.watches.backend.enums.PaymentMethod;


@Entity
@Table(name = "payments")
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

    public Payment(Double amount, PaymentMethod method, Order order) {
        this.amount = amount;
        this.method = method;
        this.order = order;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    // Getters and setters...

    public Long getId() { return id; }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }

    public PaymentStatus getStatus() { return status; }

    public void setStatus(PaymentStatus status) { this.status = status; }

    public PaymentMethod getMethod() { return method; }

    public void setMethod(PaymentMethod method) { this.method = method; }

    public Order getOrder() { return order; }

    public void setOrder(Order order) { this.order = order; }
}
