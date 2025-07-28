package com.watches.backend.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {

      super("Could not find Order with id: " + id);
    }
}
