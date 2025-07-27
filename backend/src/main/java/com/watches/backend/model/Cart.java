package com.watches.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
     private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProductItem> items;

    public Cart() {
    }

    public Cart(Customer customer, List<ProductItem> items) {
        this.customer = customer;
        customer.setCart(this);
        this.items = items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCustomer(Customer customer) {this.customer = customer;}
    public Customer getCustomer() {return customer;}

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
    public List<ProductItem> getItems() {return items;}
    public void addItem(ProductItem item){this.items.add(item);}

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + customer +
                ", items=" + items +
                '}';
    }
}
