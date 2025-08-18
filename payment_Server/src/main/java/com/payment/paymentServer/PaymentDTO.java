package com.payment.paymentServer;


public class PaymentDTO {
     private String cardNumber;
     private Double amount;

    private String expirationDate;

    private String cvv;
    private String billingAddress;
    private String postalCode;

     private CardType cardType;
     private Long userId;
     private String companyName;

    public PaymentDTO(String cardNumber,
                      Double amount,
                      String expirationDate,
                      String cvv,
                      String billingAddress,
                      String postalCode,
                      CardType cardType,
                      Long userId,
                      String companyName) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.postalCode = postalCode;
        this.cardType = cardType;
        this.userId = userId;
        this.companyName = companyName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "PaymentDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", amount=" + amount +
                ", expiryDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", cardType=" + cardType +
                ", userId=" + userId +
                ", companyName='" + companyName + '\'' +
                '}';
    }


}
