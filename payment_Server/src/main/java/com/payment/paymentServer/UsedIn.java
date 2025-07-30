package com.payment.paymentServer;

import jakarta.persistence.*;

@Entity
public class UsedIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long userId;
    private String companyName;
    @ManyToOne
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;

    public UsedIn() {

    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public UsedIn(Long userId, String companyName ,PaymentInfo paymentInfo) {
        this.userId = userId;
        this.companyName = companyName;
        this.paymentInfo = paymentInfo;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
}
