package com.watches.backend.Repositories;

import com.watches.backend.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long>{
}
