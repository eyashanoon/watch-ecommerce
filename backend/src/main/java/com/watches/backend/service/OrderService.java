//package com.watches.backend.service;
//
//import com.watches.backend.Dto.OrderDto.*;
//import com.watches.backend.exception.ResourceNotFoundException;
//import com.watches.backend.mappers.OrderMapper;
//import com.watches.backend.model.*;
//import com.watches.backend.repositories.*;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//    private final CustomerRepository customerRepository;
//    private final ProductRepository productRepository;
//    private final OrderItemRepository orderItemRepository;
//
//    public OrderService(OrderRepository orderRepository,
//                        CustomerRepository customerRepository,
//                        ProductRepository productRepository,
//                        OrderItemRepository orderItemRepository) {
//        this.orderRepository = orderRepository;
//        this.customerRepository = customerRepository;
//        this.productRepository = productRepository;
//        this.orderItemRepository = orderItemRepository;
//    }
//
//    // ========== CREATE ==========
//    @Transactional
//    public OrderDTO createOrder(CreateOrderDTO dto) {
//        // Fetch customer
//        Customer customer = customerRepository.findById(dto.getCustomerId())
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
//
//        // Create empty order first (to assign items later)
//        Order order = OrderMapper.fromCreateDTO(dto, customer);
//        order = orderRepository.save(order);
//
//        double total = 0;
//
//        // Add items
//        for (CreateOrderItemDTO itemDTO : dto.getItems()) {
//            Product product = productRepository.findById(itemDTO.getProductId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//
//            OrderItem item = OrderMapper.fromCreateItemDTO(itemDTO, product, order);
//            orderItemRepository.save(item);
//
//            total += product.getPrice() * itemDTO.getQuantity();
//        }
//
//        order.setTotalPrice(total);
//        orderRepository.save(order); // update total price
//
//        return OrderMapper.toDTO(order);
//    }
//
//    // ========== READ ==========
//    public OrderDTO getOrderById(Long id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
//
//        return OrderMapper.toDTO(order);
//    }
//
//    public List<OrderDTO> getAllOrders() {
//        return orderRepository.findAll().stream()
//                .map(OrderMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    // ========== UPDATE ==========
//    @Transactional
//    public OrderDTO updateOrderStatus(Long id, UpdateOrderStatusDTO dto) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
//
//        OrderMapper.updateStatus(order, dto);
//        orderRepository.save(order);
//
//        return OrderMapper.toDTO(order);
//    }
//}
