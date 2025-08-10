package com.watches.backend.controller;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.helpers.CustomerQueryObject;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AuthController authController;

    @PostMapping
    AuthController.AuthResponse createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        customerService.createCustomer(createCustomerDTO).join();
        return authController.login(new AuthController.AuthRequest(createCustomerDTO.getEmail(), createCustomerDTO.getPassword()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CUSTOMER')")
    CustomerDTO getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id).join();
        return CustomerMapper.toDTO(customer);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CUSTOMER')")
    Page<CustomerDTO> getAllCustomers(@RequestBody CustomerQueryObject queryObject) {
       Page<Customer> customer = customerService.getAllCustomers(queryObject).join();
       return customer.map(CustomerMapper::toDTO);
    }

    @GetMapping("/me")
    CustomerDTO getMyCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerService.getCustomerByUsername(username).join();
        return CustomerMapper.toDTO(customer);
    }

    @PutMapping
    CustomerDTO updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Customer customer = customerService.updateCustomer(username, updateCustomerDTO).join();
        return CustomerMapper.toDTO(customer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('DELETE_CUSTOMER')")
    void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
