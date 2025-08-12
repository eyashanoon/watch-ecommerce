 
package com.watches.backend.service;

import com.watches.backend.Dto.PaymentDto.PaymentDTO;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.mappers.OrderMapper;
import com.watches.backend.model.*;
import com.watches.backend.Repositories.*;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final  AuthService authService;
    private final SavedCardRepository savedCardRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public CompletableFuture<Order> createOrder(String username, Map<Long, Integer> items) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        Customer customer = customerService.getCustomerByUsername(username).join();
        SavedCard card = customer.getSavedCard();
        if(card == null){
            throw CException.badRequest(Order.class, "Please fill your card info before ordering");
        }
        for(Map.Entry<Long, Integer> entry : items.entrySet()) {
            Product product = productService.findByIdAsync(entry.getKey()).join();
            if(product.getQuantity() >= entry.getValue()) {
                OrderItem orderItem = new OrderItem(product, order, entry.getValue());
                orderItems.add(orderItem);
                order.addItem(orderItem);
                product.setQuantity(product.getQuantity() - entry.getValue());
                productService.saveAfterDiscount(product);
            }else{
                throw CException.badRequest(Order.class, "Product with id " + entry.getKey() + " does not have enough quantity");
            }
        }
        customer.addOrder(order);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        customerService.save(customer);
        makePayment(new PaymentDTO(
                card.getCardNumber(),
                order.getTotalPrice(),
                card.getExpirationDate(),
                card.getCvv(),
                card.getBillingAddress(),
                card.getPostalCode(),
                card.getCardType(),
                customer.getId(),
                "La-Royal"
        )).join();
        return CompletableFuture.completedFuture(order);
    }

    public CompletableFuture<Order> getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> CException.notFound(Order.class, "id", id));

        return CompletableFuture.completedFuture(order);
    }

    public CompletableFuture<List<Order>> getAllOrders() {
        return CompletableFuture.completedFuture(orderRepository.findAll());
    }

    @Transactional
    public CompletableFuture<Order> updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> CException.notFound(Order.class, "id", id));

        OrderMapper.updateStatus(order, status);
        orderRepository.save(order);

        return CompletableFuture.completedFuture(order);
    }

    public CompletableFuture<ResponseEntity<String>> makePayment(PaymentDTO paymentDTO) {
        String paymentServerUrl = "http://localhost:9091/api/payment/make";

        try {
            return CompletableFuture.completedFuture(restTemplate.postForEntity(
                    paymentServerUrl,
                    paymentDTO,
                    String.class
            ));
        } catch (Exception ex) {
            throw CException.unexpected(ex);
        }
    }

    public CompletableFuture<List<Order>> getAllByUsername(String username) {
        Customer customer = customerService.getCustomerByUsername(username).join();
        return CompletableFuture.completedFuture(customer.getOrders());
    }

    public CompletableFuture<List<Order>> getAllById(Long id) {
        Customer customer = customerService.getCustomerById(id).join();
        return CompletableFuture.completedFuture(customer.getOrders());
    }
}
 