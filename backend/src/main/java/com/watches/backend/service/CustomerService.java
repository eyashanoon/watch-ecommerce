package com.watches.backend.service;

import com.watches.backend.Dto.customer.CreateCustomerDTO;
import com.watches.backend.Dto.customer.UpdateCustomerDTO;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.helpers.specification.SpecificationBuilder;
import com.watches.backend.helpers.query.UserQueryObject;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Cart;
import com.watches.backend.model.Customer;
import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.model.Wishlist;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final WishlistService wishlistService;
    private final CartService cartService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CompletableFuture<Customer> createCustomer(CreateCustomerDTO createCustomerDTO) {

        Customer customer = CustomerMapper.fromCreateDTO(createCustomerDTO);
        Cart cart = cartService.createAsync().join();
        Wishlist wishlist = wishlistService.createAsync().join();

        customer.setWishlist(wishlist);
        customer.setCart(cart);
        customer.setPassword(passwordEncoder.encode(createCustomerDTO.getPassword()));
        customerRepository.save(customer);
        return CompletableFuture.completedFuture(customer);
    }

     public CompletableFuture<Customer> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CException.notFound(Customer.class, "id", id));
        return CompletableFuture.completedFuture(customer);
    }

    public CompletableFuture<Customer> getCustomerByUsername(String username) {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> CException.notFound(Customer.class, "email", username));
        return CompletableFuture.completedFuture(customer);
    }

    public CompletableFuture<Page<Customer>> getAllCustomers(UserQueryObject queryObject) {

        Specification<Customer> spec = new SpecificationBuilder<>(Customer.class)
                .withFilter(queryObject)
                .build();

        Page<Customer> res = customerRepository.findAll(spec,
                 PageRequest.of(queryObject.getPageNumber() - 1, queryObject.getPageSize())
        );

        return CompletableFuture.completedFuture(res);
    }

     public CompletableFuture<Customer> updateCustomer(String username, UpdateCustomerDTO updateCustomerDTO) {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> CException.notFound(Customer.class, "email", username));

        customer.setUsername(username);
        customer.setEmail(updateCustomerDTO.getEmail());
        customer.setPhone(updateCustomerDTO.getPhone());
        if(customer.getPassword().equals(updateCustomerDTO.getOldPassword())){
            customer.setPassword(passwordEncoder.encode(updateCustomerDTO.getOldPassword()));
        }
        return CompletableFuture.completedFuture(customerRepository.save(customer));
    }

     public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id).join();

        customer.setDeleted(true);

        wishlistService.delete(customer.getWishlist());
        cartService.delete(customer.getCart());

        customerRepository.save(customer);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
