package com.watches.backend.exceptions;

public class WishlistNotFoundException extends RuntimeException{
    public WishlistNotFoundException(Long id){
        super("Wishlist not found with id: " + id);
    }
}
