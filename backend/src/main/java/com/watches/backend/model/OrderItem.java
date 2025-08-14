package com.watches.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Integer quantity;

    private Double priceAtPurchase=0.0;

    public OrderItem(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        setPrice();
    }

    private void setPrice(){
        Double price = product.getPrice() * (product.getDiscount() == null ? 1 : product.getDiscount().getDiscount());
        this.priceAtPurchase = price * quantity;
    }

    @PrePersist
    public void prePersist() {
        if ((priceAtPurchase == 0 || priceAtPurchase < 0) && product != null) {
            this.priceAtPurchase = product.getPrice();
        }
    }
}

