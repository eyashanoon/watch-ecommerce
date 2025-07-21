package com.watches.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
 
  public class Customer extends User {

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    private Wishlist wishlist;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedCard> savedCards = new ArrayList<>();

    public Customer(String userName, String email, String password, String phone,
                    List<Order> orders, Cart cart, Wishlist wishlist) {
        super(userName, email, password, phone);
        this.cart = cart;
        this.wishlist = wishlist;
        this.orders = new ArrayList<>();
         if (orders != null) {
            orders.forEach(this::addOrder);
        }
    }

    public Customer() {
     }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }



    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        if (cart != null) cart.setCustomer(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
        if (wishlist != null) wishlist.setCustomer(this);
    }

    public List<SavedCard> getSavedCards() {
        return savedCards;
    }

    public void setSavedCards(List<SavedCard> savedCards) {
        this.savedCards = savedCards;
    }

    public void addSavedCard(SavedCard card) {
        card.setCustomer(this);
        this.savedCards.add(card);
    }

    public void removeSavedCard(SavedCard card) {
        card.setCustomer(null);
        this.savedCards.remove(card);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
