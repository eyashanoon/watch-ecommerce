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

    public void addItem(ProductItem item){
        int exists = this.items.indexOf(item);
        if(exists > -1){
            this.items.get(exists)
                    .setQuantity(
                            this.items.indexOf(item) + item.getQuantity()
                    );
        }else{
            this.items.add(item);
        }
    }

    public void removeItem(ProductItem item){
        int exists = items.indexOf(item);
        if(exists > -1){
            ProductItem itemToRemove = this.items.get(exists);
            itemToRemove.setQuantity(itemToRemove.getQuantity() - item.getQuantity());
            if(itemToRemove.getQuantity() <= 0){
                items.remove(itemToRemove);
            }
        }
    }



    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + customer +
                ", items=" + items +
                '}';
    }
}
