package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BandRepository extends JpaRepository<Band, String> {

    @Query("SELECT DISTINCT b.material FROM Band b WHERE b.material IS NOT NULL")
    Set<String> getAllDistinctBands();

    @Query("SELECT p.band.material FROM Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);
}
