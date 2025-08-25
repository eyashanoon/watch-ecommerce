package com.watches.backend.controller;

import com.watches.backend.Dto.authentication.AuthRequest;
import com.watches.backend.Dto.authentication.AuthResponse;
import com.watches.backend.Dto.customer.CreateCustomerDTO;
import com.watches.backend.Dto.customer.UpdateCustomerDTO;
import com.watches.backend.Dto.customer.CustomerDTO;
import com.watches.backend.helpers.query.UserQueryObject;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.service.AuthService;
import com.watches.backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AuthController authController;
    private final AuthService authService;

    @PostMapping
    AuthResponse createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        customerService.createCustomer(createCustomerDTO).join();
        return authController.login(new AuthRequest(createCustomerDTO.getEmail(), createCustomerDTO.getPassword()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CUSTOMER')")
    CustomerDTO getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id).join();
        return CustomerMapper.toDTO(customer);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CUSTOMER')")
    Page<CustomerDTO> getAllCustomers(@ModelAttribute UserQueryObject queryObject) {
       Page<Customer> customer = customerService.getAllCustomers(queryObject).join();
       return customer.map(CustomerMapper::toDTO);
    }

    @GetMapping("/me")
    CustomerDTO getMyCustomer() {
        String username = authService.getCurrentUserName();
        Customer customer = customerService.getCustomerByUsername(username).join();
        return CustomerMapper.toDTO(customer);
    }

    @PutMapping
    CustomerDTO updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        String username = authService.getCurrentUserName();

        Customer customer = customerService.updateCustomer(username, updateCustomerDTO).join();
        return CustomerMapper.toDTO(customer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('DELETE_CUSTOMER')")
    void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
