package com.watches.backend.service;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.Repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

     public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = CustomerMapper.fromCreateDTO(createCustomerDTO);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toDTO(saved);
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
