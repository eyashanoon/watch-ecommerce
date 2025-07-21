package com.watches.backend.Repositories;

import com.watches.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom query methods here if needed, e.g.:
    // Optional<Customer> findByEmail(String email);
}
