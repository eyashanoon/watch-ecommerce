package com.watches.backend.exceptions;

public class SavedCardNotFoundException extends RuntimeException {
    public SavedCardNotFoundException(Long id) {
        super("Could not find card with id: " + id);
    }

}
