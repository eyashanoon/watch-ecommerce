package com.watches.backend.controller;

import com.watches.backend.Dto.order.*;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.mappers.OrderMapper;
import com.watches.backend.model.Order;
import com.watches.backend.service.AuthService;
import com.watches.backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    @PostMapping
    OrderDTO createOrderFromCart(@RequestBody Map<Long, Integer> items) {

        String username = authService.getCurrentUserName();

        Order createdOrder = orderService.createOrder(username, items).join();
        return OrderMapper.toDTO(createdOrder);
    }

    @GetMapping("/me")
    List<OrderDTO> getMyOrders() {
        String username = authService.getCurrentUserName();

        List<Order> orders = orderService.getAllByUsername(username).join();
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ORDER')")
    List<OrderDTO> getOrdersByCustomer(@PathVariable Long id) {
        List<Order> orders = orderService.getAllById(id).join();
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ORDER')")
    OrderDTO getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).join();
        return OrderMapper.toDTO(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ORDER')")
    List<OrderDTO> getAllOrders() {
        List<Order> order = orderService.getAllOrders().join();
        return order.stream().map(OrderMapper::toDTO).toList();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('OWNER') || hasRole('UPDATE_ORDER')")
    OrderDTO updateOrderStatus(@PathVariable Long id,
                                                      @Valid @RequestBody OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status).join();
        return OrderMapper.toDTO(order);
    }

}











