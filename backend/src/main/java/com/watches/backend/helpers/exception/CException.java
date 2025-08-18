package com.watches.backend.helpers.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CException extends RuntimeException{

    private final HttpStatus httpStatus;

    private CException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static CException unexpected(Throwable cause){
        return new CException(HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
    }

    public static CException conflict(String message) {
        return new CException(HttpStatus.CONFLICT, message);
    }

    public static <J> CException badRequest(Class<J> clazz, String message){
        return new CException(HttpStatus.BAD_REQUEST, clazz.getSimpleName() + ": " + message);
    }

    public static <J> CException notFound(Class<J> clazz, String field, Object value){
        return new CException(HttpStatus.NOT_FOUND,clazz.getSimpleName()+" with "+field+" "+value+" not found");
    }

    public static CException unauthorized(String message) {
        return new CException(HttpStatus.UNAUTHORIZED, message);
    }

}
