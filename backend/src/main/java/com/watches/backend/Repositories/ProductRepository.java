package com.watches.backend.Repositories;

import com.watches.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.concurrent.CompletableFuture;

public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product,Long> {
}
