package com.watches.backend.exceptions;

public class CustomerNotFoundException extends RuntimeException{
 

    public  CustomerNotFoundException(Long id){
        super("Customer not found with id: " + id);
    }

    public  CustomerNotFoundException(String username){
        super("Customer not found with id: " + username);
    }

 }
