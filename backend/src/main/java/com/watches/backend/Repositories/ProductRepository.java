package com.watches.backend.Repositories;

import com.watches.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.CompletableFuture;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
