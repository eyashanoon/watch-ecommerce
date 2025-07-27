package com.watches.backend.service;

import com.watches.backend.Dto.OrderDto.*;
 import com.watches.backend.exceptions.*;
import com.watches.backend.mappers.OrderMapper;
import com.watches.backend.model.*;
import com.watches.backend.Repositories.*;

import com.watches.backend.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final  AuthService authService;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        AuthService authService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository=cartRepository;
        this.authService = authService;

    }

    // ========== CREATE ==========
    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto) {
        // Fetch customer
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.getCustomerId()));

        // Create empty order first (to assign items later)
        Order order = OrderMapper.fromCreateDTO(dto, customer);
        order = orderRepository.save(order);

        double total = 0;

        // Add items
        for (CreateOrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId()));

            OrderItem item = OrderMapper.fromCreateItemDTO(itemDTO, product, order);
            orderItemRepository.save(item);

         }

         orderRepository.save(order); // update total price

        return OrderMapper.toDTO(order);
    }
    @Transactional
    public OrderDTO createOrderFromCart(Long cartID) {
        Cart cart=cartRepository.findById(cartID).orElseThrow(() -> new CartNotFoundException(cartID));
        CreateOrderDTO createOrderDTO=OrderMapper.fromCartToCreateOrderDTO(cart);
        return createOrder(createOrderDTO);

    }

    // ========== READ ==========
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
    public OrderDTO updateOrderStatus(Long id, UpdateOrderStatusDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new   OrderNotFoundException(id));

        OrderMapper.updateStatus(order, dto);
        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }
     public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
       return orderRepository.findByCustomer(customer).stream().map(OrderMapper::toDTO).collect(Collectors.toList());

    }
    public List<OrderDTO> getOrdersByCustomerId() {

        Long customerId= authService.getCurrentUserId();
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return orderRepository.findByCustomer(customer).stream().map(OrderMapper::toDTO).collect(Collectors.toList());

    }
}
