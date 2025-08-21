package com.watches.backend.Repositories;

import com.watches.backend.Dto.report.IReport;
import com.watches.backend.model.Product;
 import com.watches.backend.model.productFeatures.*;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product,Long> {
 
    List<Product> findByBrand(Brand brand);
    List<Product> findByBand(Band band);
    List<Product> findByaCase(Case aCase);
    List<Product> findByDisplayType(DisplayType type);
    List<Product> findByNumberingFormat(NumberingFormat format);
    List<Product> findByShape(Shape brand);

    List<Product> findAllByName(String name);

    @Query("SELECT new com.watches.backend.Dto.report.YearReportDto(YEAR(o.placedAt), SUM(oi.quantity)) " +
            "FROM Order o " +
            "LEFT JOIN OrderItem oi ON o.id = oi.order.id " +
            "LEFT JOIN Product p ON p.id = oi.product.id " +
            "WHERE YEAR(o.placedAt) <= :year AND YEAR(o.placedAt) >= (:year - 5)" +
            "GROUP BY YEAR(o.placedAt) " +
            "ORDER BY YEAR(o.placedAt) ")
    List<IReport> countProductGroupedByYear(@Param("feature") String feature, @Param("year") int year);

}
 
