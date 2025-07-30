package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
