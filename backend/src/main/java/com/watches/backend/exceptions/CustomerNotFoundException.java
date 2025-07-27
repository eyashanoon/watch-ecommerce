package com.watches.backend.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Could not find Customer with id: " + id);
    }
}
