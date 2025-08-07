package com.watches.backend.controller;

import com.watches.backend.Dto.OrderDto.*;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.mappers.OrderMapper;
import com.watches.backend.model.Order;
import com.watches.backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    OrderDTO createOrderFromCart(@RequestBody Map<Long, Integer> items) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Order createdOrder = orderService.createOrder(username, items).join();
        return OrderMapper.toDTO(createdOrder);
    }

    @GetMapping("/me")
    List<OrderDTO> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Order> orders = orderService.getAllByUsername(username).join();
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

    @GetMapping("/customer/{id}")
    List<OrderDTO> getOrdersByCustomer(@PathVariable Long id) {
        List<Order> orders = orderService.getAllById(id).join();
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id,
                                                      @Valid @RequestBody OrderStatus status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

}











