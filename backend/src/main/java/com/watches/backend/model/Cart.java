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

    public void setCustomer(Customer customer) {this.customer = customer;}
    public Customer getCustomer() {return customer;}

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
    public List<ProductItem> getItems() {return items;}
    public void addItem(ProductItem item){this.items.add(item);}
}
