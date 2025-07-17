package com.watches.backend.model;
import jakarta.persistence.*;


@Entity
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

    private int quantity;
    private double priceAtPurchase;

    public OrderItem(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
     }
    public OrderItem() {}

    public Product getProduct(){
        return this.product;
    }
    public double getPriceAtPurchase(){
        return this.priceAtPurchase;
    }

    public Order getOrder() {
        return order;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setProduct(Product product){
        this.product=product;
    }
    public void setOrder(Order order){
        this.order=order;
    }
    @PrePersist
    public void prePersist() {
        if ((priceAtPurchase == 0 || priceAtPurchase < 0) && product != null) {
            this.priceAtPurchase = product.getPrice();
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

