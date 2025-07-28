package com.watches.backend.advices;

import com.watches.backend.exceptions.DiscountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DiscountNotFoundAdvice {

    @ExceptionHandler(DiscountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> discountNotFoundException(DiscountNotFoundException ex) {
        Map<String,String> error = new HashMap<>();
        error.put("DiscountNotFoundException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
