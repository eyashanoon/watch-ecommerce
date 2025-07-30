 
package com.watches.backend.service;

import com.watches.backend.Dto.OrderDto.*;
import com.watches.backend.Dto.PaymentDto.PaymentDTO;
import com.watches.backend.enums.PaymentMethod;
import com.watches.backend.enums.PaymentStatus;
import com.watches.backend.exceptions.*;
import com.watches.backend.mappers.OrderMapper;
import com.watches.backend.model.*;
import com.watches.backend.Repositories.*;

import com.watches.backend.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

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
    private final SavedCardRepository savedCardRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        AuthService authService, SavedCardRepository savedCardRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository=cartRepository;
        this.authService = authService;
        this.savedCardRepository = savedCardRepository;
        this.restTemplate = restTemplate;
    }

    // ========== CREATE ==========
    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto) {
        System.out.println(dto);
        // Fetch customer
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.getCustomerId()));
        Double totalPrice = dto.getItems().stream().mapToDouble(item->
                item.getQuantity()* ((Product)productRepository.findById(item.getProductId()).orElseThrow(()->new ProductNotFoundException(item.getProductId()))).getPrice()).sum();

        Payment payment = new Payment(totalPrice, dto.getCreatePaymentDTO().getPaymentMethod());

        if (payment.getMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            payment.setStatus(PaymentStatus.WAITING_FOR_DELIVERY);
        } else if (payment.getMethod() == PaymentMethod.CREDIT_CARD) {
            SavedCard savedCard = savedCardRepository.findByCustomer(customer);
            if (savedCard == null) {
                throw new OrderPaymentFailed("No saved card found for this customer");
            }

            PaymentDTO paymentDTO = new PaymentDTO(
                    savedCard.getCardNumber(),
                    totalPrice,
                    savedCard.getExpiryDate(),
                    savedCard.getCvv()
            );
            paymentDTO.setUserId(customer.getId());
            paymentDTO.setCompanyName("MyCompany");
            paymentDTO.setCardType(savedCard.getCardType());

            ResponseEntity<String> response = makePayment(paymentDTO);
            if (response.getStatusCode() == HttpStatus.OK) {
                payment.setStatus(PaymentStatus.COMPLETED);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                throw new OrderPaymentFailed("Payment failed");
            }
        }


        // Create order and add items
        Order order = OrderMapper.fromCreateDTO(dto, customer);
        for (CreateOrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId()));
            OrderItem item = OrderMapper.fromCreateItemDTO(itemDTO, product, order);
            order.addItem(item); // Make sure addItem sets both sides of the relationship
        }

         order.setPayment(payment);

        // Save order (cascades should save items and payment)
        orderRepository.save(order);

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
}
 