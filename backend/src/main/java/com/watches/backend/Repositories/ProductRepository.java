package com.watches.backend.Repositories;

import com.watches.backend.model.Product;
 import com.watches.backend.model.productFeatures.*;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product,Long> {
 
    List<Product> findByBrand(Brand brand);
    List<Product> findByBand(Band band);
    List<Product> findByaCase(Case aCase);
    List<Product> findByDisplayType(DisplayType type);
    List<Product> findByNumberingFormat(NumberingFormat format);
    List<Product> findByShape(Shape brand);

}
 
