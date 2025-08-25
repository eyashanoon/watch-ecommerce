package com.watches.backend.Repositories;

import com.watches.backend.Dto.report.ReportDto;
import com.watches.backend.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product,Long> {

    List<Product> findAllByName(String name);

    @Query("SELECT new com.watches.backend.Dto.report.ReportDto(YEAR(o.placedAt), COALESCE(SUM(oi.quantity), 0)) " +
            "FROM Order o " +
            "LEFT JOIN OrderItem oi ON o.id = oi.order.id " +
            "LEFT JOIN Product p ON p.id = oi.product.id " +
            "WHERE YEAR(o.placedAt) <= :year AND YEAR(o.placedAt) >= (:year - 5)" +
            "GROUP BY YEAR(o.placedAt) " +
            "ORDER BY YEAR(o.placedAt) ")
    List<ReportDto<Integer>> countProductGroupedByYear(@Param("year") int year);

    @Query("SELECT new com.watches.backend.Dto.report.ReportDto(MONTH(o.placedAt), COALESCE(SUM(oi.quantity), 0)) " +
            "FROM Order o " +
            "LEFT JOIN OrderItem oi ON o.id = oi.order.id " +
            "LEFT JOIN Product p ON p.id = oi.product.id " +
            "WHERE YEAR(o.placedAt) = :year " +
            "GROUP BY MONTH(o.placedAt) " +
            "ORDER BY Month(o.placedAt)")
    List<ReportDto<Integer>> countMonthlyProductGroupedByMonth(@Param("year") int year);

    @Query("SELECT new com.watches.backend.Dto.report.ReportDto(DAY(o.placedAt), COALESCE(SUM(oi.quantity), 0)) " +
            "FROM Order o " +
            "LEFT JOIN OrderItem oi ON o.id = oi.order.id " +
            "LEFT JOIN Product p ON p.id = oi.product.id " +
            "WHERE YEAR(o.placedAt) = :year AND MONTH(o.placedAt) = :month " +
            "GROUP BY DAY(o.placedAt) " +
            "ORDER BY DAY(o.placedAt)")
    List<ReportDto<Integer>> countDailyProductGroupedByDay(@Param("year") int year, @Param("month") String month);

}
 
