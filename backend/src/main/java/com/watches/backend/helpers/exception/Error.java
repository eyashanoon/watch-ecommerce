package com.watches.backend.helpers.exception;

import org.springframework.http.ResponseEntity;


public record Error(
        int status,
        String message,
        String details,
        long timestamp
) {

    public Error{
        if (status < 400) {
            throw new IllegalArgumentException("Status must be greater than or equal to 400");
        }
    }

    public static Error from(CException ex){
        return new Error(
                ex.getHttpStatus().value(),
                ex.getHttpStatus().getReasonPhrase(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
    }

    public ResponseEntity<Error> to(){
        return ResponseEntity.status(status)
                .body(this);
    }

}
