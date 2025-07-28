package com.watches.backend.exceptions;

public class DiscountNotFoundException extends RuntimeException{

    public DiscountNotFoundException(Long id) {
        super("Discount not found with id: " + id);
    }
}
