package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CaseRepository extends JpaRepository<Case, String> {

    @Query("SELECT DISTINCT c.material FROM Case c WHERE c.material IS NOT NULL")
    Set<String> findAllDistinctCases();

    @Query("SELECT p.aCase.material FROM Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);

}
