package com.watches.backend.model;

import com.watches.backend.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    private Wishlist wishlist;

    @OneToOne
    @JoinColumn(name = "card_id")
    private SavedCard savedCard;

    public Customer(String userName, String email, String password, String phone,
                    List<Order> orders, Cart cart, Wishlist wishlist) {
        super(userName, email, password, phone);
        this.cart = cart;
        this.wishlist = wishlist;
        this.orders = new ArrayList<>();
        if (orders != null) {
            orders.forEach(this::addOrder);
        }
        this.setRoles(Set.of(Role.CUSTOMER));
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        if (cart != null) cart.setCustomer(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
        if (wishlist != null) wishlist.setCustomer(this);
    }

}