package com.watches.backend.advices;

import com.watches.backend.exceptions.WishlistNotFoundException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WishlistNotFoundAdvice {

    @ExceptionHandler(WishlistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(WishlistNotFoundException ex){
        return ex.getMessage();
    }

}
