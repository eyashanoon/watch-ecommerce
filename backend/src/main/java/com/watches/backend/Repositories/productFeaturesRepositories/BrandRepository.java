package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BrandRepository extends JpaRepository<Brand, String> {

    @Query("SELECT DISTINCT b.name FROM Brand b WHERE b.name IS NOT NULL")
    Set<String> findAllDistinctBrands();

    @Query("SELECT p.brand.name FROM Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);
}
