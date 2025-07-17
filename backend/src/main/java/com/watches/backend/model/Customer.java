package com.watches.backend.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private final String  role = "CUSTOMER";
    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "wish_list_id", referencedColumnName = "id")
    private Wishlist wishlist;


    public Customer(String userName,String email, String password, String phone ,List<Order> orders, Cart cart,Wishlist wishlist) {
        super(userName,email, password, phone);
        this.cart=cart;
        this.wishlist=wishlist;
        orders.forEach(this:: addOrder);

    }
    public Customer() {}

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void addOrder(Order order){
        this.orders.add(order);
        order.setCustomer(this);
    }
    public Wishlist getWishlist(){
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;

    }
}
