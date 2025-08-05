package com.watches.backend.controller;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.model.Customer;
import com.watches.backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public AuthController.AuthResponse createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        CompletableFuture<Customer> createdCustomer = customerService.createCustomer(createCustomerDTO);
        Customer compCustomer = createdCustomer.join();
        return authController.login(new AuthController.AuthRequest(compCustomer.getEmail(), createCustomerDTO.getPassword()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, updateCustomerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
