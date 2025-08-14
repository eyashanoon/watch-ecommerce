package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ShapeRepository extends JpaRepository<Shape, String> {

    @Query("SELECT DISTINCT s.name FROM Shape s WHERE s.name IS NOT NULL")
    Set<String> findAllDistinctShapes();

    @Query("SELECT p.shape.name FROM Product p WHERE p.id = :productId")
    String findByProductId(@Param("productId") Long productId);

}
