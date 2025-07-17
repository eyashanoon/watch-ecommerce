package com.watches.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private String id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
     private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProductItem> items;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
