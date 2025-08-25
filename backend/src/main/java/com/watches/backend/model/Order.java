package com.watches.backend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import  com.watches.backend.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus  status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Customer customer,List<OrderItem> items ){
        items.forEach(this::addItem);
        this.customer=customer;
    }

    @PrePersist
    public void prePersist() {
        placedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = OrderStatus.REQUESTED;
    }
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

     public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public double getTotalPrice(){
        return items.stream().mapToDouble(item->item.getPriceAtPurchase() * item.getQuantity()).sum();
    }

}
