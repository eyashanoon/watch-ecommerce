package com.watches.backend.Repositories;

import com.watches.backend.model.Customer;
import com.watches.backend.model.SavedCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedCardRepository extends JpaRepository<SavedCard,Long> {

    SavedCard findByCustomer(Customer customer);

    void deleteByCustomer(Customer customer);
}
