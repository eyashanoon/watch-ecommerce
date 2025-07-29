package com.watches.backend.exceptions;

public class OrderPaymentFailed extends RuntimeException{
    public OrderPaymentFailed(String message) {
        super(message);
    }
}
