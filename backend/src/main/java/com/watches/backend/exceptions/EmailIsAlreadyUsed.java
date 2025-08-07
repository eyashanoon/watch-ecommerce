package com.watches.backend.exceptions;

public class EmailIsAlreadyUsed extends RuntimeException{
    public EmailIsAlreadyUsed(String email){
        super(email+" is used");
    }
}
