package com.watches.backend.controller;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AuthController authController;

    @PostMapping
    AuthController.AuthResponse createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        CompletableFuture<Customer> createdCustomer = customerService.createCustomer(createCustomerDTO);
        Customer compCustomer = createdCustomer.join();
        return authController.login(new AuthController.AuthRequest(compCustomer.getEmail(), createCustomerDTO.getPassword()));
    }

    @GetMapping("/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id).join();
        return CustomerMapper.toDTO(customer);
    }

    @GetMapping
    List<CustomerDTO> getAllCustomers() {
       List<Customer> customer = customerService.getAllCustomers().join();
       return customer.stream().map(CustomerMapper::toDTO).toList();
    }

    @PutMapping
    CustomerDTO updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Customer customer = customerService.updateCustomer(username, updateCustomerDTO).join();
        return CustomerMapper.toDTO(customer);
    }

    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
