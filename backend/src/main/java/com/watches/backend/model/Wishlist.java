package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "wishlist", cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "product_wishlist",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    private boolean deleted;

    public Wishlist(Customer customer, List<Product> products) {
        this.customer = customer;
        if (products != null) {
            products.forEach(this::addItem);
        }
    }

    public void addItem(Product product) {
        boolean contains = false;

        for(Product p : products){
            if(p.getId().equals(product.getId())){
                contains = true;
                break;
            }
        }

        if (!contains) {
            this.products.add(product);
        }
    }

    public void removeItem(Product product) {

        for(Product p : products){
            if(p.getId().equals(product.getId())){
                products.remove(p);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", customer=" + customer +
                ", products=" + products +
                '}';
    }


}
