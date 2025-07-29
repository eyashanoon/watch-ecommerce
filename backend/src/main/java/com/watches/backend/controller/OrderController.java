package com.watches.backend.controller;

import com.watches.backend.Dto.OrderDto.*;
import com.watches.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ========== CREATE ==========
    @PostMapping
    //@PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderDTO dto) {
        OrderDTO createdOrder = orderService.createOrder(dto);
        return ResponseEntity.ok(createdOrder);
    }
    @PostMapping("/{cartID}/create-from-cart")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<OrderDTO> createOrderFromCart(@PathVariable Long cartID) {
        OrderDTO createdOrder = orderService.createOrderFromCart(cartID);
        return ResponseEntity.ok(createdOrder);
    }

    // ========== READ ==========
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // ========== UPDATE ==========
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id,
                                                      @Valid @RequestBody UpdateOrderStatusDTO dto) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, dto);
        return ResponseEntity.ok(updatedOrder);
    }
    @GetMapping("/{customerID}/fromCustomer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersFromCustomer(@PathVariable Long customerID) {
        List<OrderDTO> orders=orderService.getOrdersByCustomerId(customerID);
         return ResponseEntity.ok(orders);
    }
    @GetMapping("/fromCustomer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderDTO>> getOrdersFromCustomer() {
        List<OrderDTO> orders=orderService.getOrdersByCustomerId();
        return ResponseEntity.ok(orders);
    }


}











