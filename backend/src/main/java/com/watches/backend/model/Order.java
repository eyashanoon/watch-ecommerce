package com.watches.backend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import  com.watches.backend.enums.OrderStatus;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;
    private OrderStatus  status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Customer customer,List<OrderItem> items ){
        items.forEach(this::addItem);
        this.customer=customer;
    }
    public Order(){}
    @PrePersist
    public void prePersist() {
        placedAt = LocalDateTime.now();
        status = OrderStatus.REQUESTED;
    }
    @PreUpdate
    public void onUpdate() {
        if (this.status==OrderStatus.REQUESTED){
            updatedAt = LocalDateTime.now();
        }
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

     public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

     public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
    public LocalDateTime getPlacedAt(){
        return placedAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public double getTotalPrice(){

        return items.stream().mapToDouble(item->item.getPriceAtPurchase()* item.getQuantity()).sum();


    }
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
