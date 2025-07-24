 package com.watches.backend.Dto;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;  // support multiple roles

    private Long cartId;
    private Long wishlistId;
    private List<Long> orderIds;
    private List<Long> savedCardIds;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String username, String email, String phone,
                       Long cartId, Long wishlistId, List<Long> orderIds, List<Long> savedCardIds, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
        this.cartId = cartId;
        this.wishlistId = wishlistId;
        this.orderIds = orderIds;
        this.savedCardIds = savedCardIds;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public List<Long> getSavedCardIds() {
        return savedCardIds;
    }

    public void setSavedCardIds(List<Long> savedCardIds) {
        this.savedCardIds = savedCardIds;
    }
}
