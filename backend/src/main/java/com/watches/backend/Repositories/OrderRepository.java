package com.watches.backend.Repositories;

import com.watches.backend.model.Customer;
import com.watches.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
     Optional<Order> findByCustomer(Customer customer);
}
