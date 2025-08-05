package com.watches.backend.service;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.Repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final WishlistService wishlistService;
    private final CartService cartService;

    private final PasswordEncoder passwordEncoder;

    @Async
    public CompletableFuture<Customer> createCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = CustomerMapper.fromCreateDTO(createCustomerDTO);
        customer.setPassword(passwordEncoder.encode(createCustomerDTO.getPassword()));
        customer.setWishlist(wishlistService.createAsync().join());
        customer.setCart(cartService.createAsync().join());
        Customer saved = customerRepository.save(customer);
        return CompletableFuture.completedFuture(saved);
    }

     public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        return CustomerMapper.toDTO(customer);
    }

     public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

     public CustomerDTO updateCustomer(Long id, UpdateCustomerDTO updateCustomerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));

        CustomerMapper.updateCustomerFromDTO(updateCustomerDTO, customer);
        Customer updated = customerRepository.save(customer);
        return CustomerMapper.toDTO(updated);
    }

     public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id " + id);
        }

        customerRepository.deleteById(id);
    }
}
