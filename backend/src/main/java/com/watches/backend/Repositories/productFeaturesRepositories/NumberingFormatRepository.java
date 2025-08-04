package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.NumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface NumberingFormatRepository extends JpaRepository<NumberingFormat, String> {

    @Query("SELECT DISTINCT n.format FROM NumberingFormat n WHERE n.format IS NOT NULL")
    Set<String> findAllDistinctFormats();

    @Query("SELECT p.numberingFormat.format FROM Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);

}
