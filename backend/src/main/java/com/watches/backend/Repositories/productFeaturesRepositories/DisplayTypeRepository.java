package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.DisplayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DisplayTypeRepository extends JpaRepository<DisplayType, String> {

    @Query("SELECT DISTINCT d.type from DisplayType d WHERE d.type IS NOT NULL")
    Set<String> findAllDistinctDisplayType();

    @Query("SELECT p.displayType.type from Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);

}
