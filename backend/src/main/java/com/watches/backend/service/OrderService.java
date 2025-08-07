 
package com.watches.backend.service;

import com.watches.backend.Dto.OrderDto.*;
import com.watches.backend.Dto.PaymentDto.PaymentDTO;
import com.watches.backend.enums.OrderStatus;
import com.watches.backend.exceptions.*;
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
import java.util.stream.Collectors;

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

//    @Transactional
//    public OrderDTO createOrder(CreateOrderDTO dto) {
//        System.out.println(dto);
//        // Fetch customer
//        Customer customer = customerRepository.findById(dto.getCustomerId())
//                .orElseThrow(() -> new CustomerNotFoundException(dto.getCustomerId()));
//        Double totalPrice = dto.getItems().stream().mapToDouble(item->
//                item.getQuantity()* ((Product)productRepository.findById(item.getProductId()).orElseThrow(()->new ProductNotFoundException(item.getProductId()))).getPrice()).sum();
//
//        Payment payment = new Payment(totalPrice, dto.getCreatePaymentDTO().getPaymentMethod());
//
//        if (payment.getMethod() == PaymentMethod.CASH_ON_DELIVERY) {
//            payment.setStatus(PaymentStatus.WAITING_FOR_DELIVERY);
//        } else if (payment.getMethod() == PaymentMethod.CREDIT_CARD) {
//            SavedCard savedCard = savedCardRepository.findByCustomer(customer);
//            if (savedCard == null) {
//                throw new OrderPaymentFailed("No saved card found for this customer");
//            }
//
//            PaymentDTO paymentDTO = new PaymentDTO(
//                    savedCard.getCardNumber(),
//                    totalPrice,
//                    savedCard.getExpiryDate(),
//                    savedCard.getCvv()
//            );
//            paymentDTO.setUserId(customer.getId());
//            paymentDTO.setCompanyName("MyCompany");
//            paymentDTO.setCardType(savedCard.getCardType());
//
//            ResponseEntity<String> response = makePayment(paymentDTO);
//            if (response.getStatusCode() == HttpStatus.OK) {
//                payment.setStatus(PaymentStatus.COMPLETED);
//            } else {
//                payment.setStatus(PaymentStatus.FAILED);
//                throw new OrderPaymentFailed("Payment failed");
//            }
//        }
//
//
//        // Create order and add items
//        Order order = OrderMapper.fromCreateDTO(dto, customer);
//        for (CreateOrderItemDTO itemDTO : dto.getItems()) {
//            Product product = productRepository.findById(itemDTO.getProductId())
//                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId()));
//            OrderItem item = OrderMapper.fromCreateItemDTO(itemDTO, product, order);
//            order.addItem(item); // Make sure addItem sets both sides of the relationship
//        }
//
//        order.setPayment(payment);
//
//        // Save order (cascades should save items and payment)
//        orderRepository.save(order);
//
//        return OrderMapper.toDTO(order);
//    }

    @Transactional
    public CompletableFuture<Order> createOrder(String username, Map<Long, Integer> items) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        for(Map.Entry<Long, Integer> entry : items.entrySet()) {
            Product product = productService.findByIdAsync(entry.getKey()).join();
            OrderItem orderItem = new OrderItem(product, order, entry.getValue());
            orderItems.add(orderItem);
            order.addItem(orderItem);
        }
        Customer customer = customerService.getCustomerByUsername(username).join();
        customer.addOrder(order);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        customerService.save(customer);
        return CompletableFuture.completedFuture(order);
    }


    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return OrderMapper.toDTO(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ========== UPDATE ==========
    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new   OrderNotFoundException(id));

        OrderMapper.updateStatus(order, status);
        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }
//     public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
//        Customer customer=customerRepository.findById(customerId)
//                .orElseThrow(() -> new CustomerNotFoundException(customerId));
//       return orderRepository.findByCustomer(customer).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
//
//    }
//    public List<OrderDTO> getOrdersByCustomerId() {
//
//        Long customerId= authService.getCurrentUserId();
//        Customer customer=customerRepository.findById(customerId)
//                .orElseThrow(() -> new CustomerNotFoundException(customerId));
//        return orderRepository.findByCustomer(customer).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
//
//    }
    public ResponseEntity<String> makePayment(PaymentDTO paymentDTO) {
        String paymentServerUrl = "http://localhost:9091/api/payment/make";

        try {
            return restTemplate.postForEntity(
                    paymentServerUrl,
                    paymentDTO,
                    String.class
            );
        } catch (Exception ex) {
            System.err.println("Payment server error: " + ex.getMessage());
            throw new RuntimeException("Failed to connect to payment server", ex);
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
 