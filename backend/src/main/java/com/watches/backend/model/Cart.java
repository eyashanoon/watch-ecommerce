package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProductItem> items;

    private Boolean deleted;

    public void addItem(ProductItem item){
        int exists = this.items.indexOf(item);
        if(exists > -1){
            this.items.get(exists)
                    .setQuantity(
                            this.items.get(exists).getQuantity() + item.getQuantity()
                    );
            this.items.get(exists)
                    .setPrice(this.items.get(exists).getProduct().getPrice() * item.getQuantity());
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
