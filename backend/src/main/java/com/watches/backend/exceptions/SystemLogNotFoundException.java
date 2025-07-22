package com.watches.backend.exceptions;

public class SystemLogNotFoundException extends RuntimeException{

    public SystemLogNotFoundException(Long id){
        super("SystemLog not found with id: " + id);
    }

}
