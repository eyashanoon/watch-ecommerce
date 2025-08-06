package com.watches.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discount")
    private Set<Product> products;

    @Positive
    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private boolean expired;

    public void addProduct(Product product) {
        for(Product p : products) {
            if(p.getId().equals(product.getId())) {
                return;
            }
        }

        products.add(product);
    }

    public boolean removeProduct(Product product) {
        for(Product p : products) {
            if(p.getId().equals(product.getId())) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }

    public List<Long> getProductsId(){
        List<Long> res = new ArrayList<>();
        for(Product p : products) {
            res.add(p.getId());
        }
        return res;
    }

}
