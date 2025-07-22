package com.watches.backend.advices;

import com.watches.backend.exceptions.SystemLogNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SystemLogNotFoundAdvice {

    @ExceptionHandler(SystemLogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFoundException(SystemLogNotFoundException ex){
        return ex.getMessage();
    }
}
