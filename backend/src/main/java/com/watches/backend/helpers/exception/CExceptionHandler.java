package com.watches.backend.helpers.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CExceptionHandler {

    @ExceptionHandler(CException.class)
    public ResponseEntity<Error> notFound(CException ex){
        return Error.from(ex).to();
    }

}
