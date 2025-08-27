package com.watches.backend.Repositories;

import com.watches.backend.Dto.report.ReportDto;
import com.watches.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

     @Query("SELECT new com.watches.backend.Dto.report.ReportDto(YEAR(o.placedAt), COUNT(o))" +
             "FROM Order o " +
             "WHERE YEAR(o.placedAt) BETWEEN :year - 5 AND :year " +
             "GROUP BY YEAR(o.placedAt) " +
             "ORDER BY YEAR(o.placedAt)"
     )
     List<ReportDto<Integer>> countOrderGroupedByYear(@Param("year") int year);

     @Query("SELECT new com.watches.backend.Dto.report.ReportDto(MONTH(o.placedAt), COUNT(o)) " +
             "FROM Order o " +
             "WHERE YEAR(o.placedAt) = :year " +
             "GROUP BY MONTH(o.placedAt) " +
             "ORDER BY MONTH(o.placedAt)")
     List<ReportDto<Integer>> countOrderMonthGroupedByMonth(@Param("year") int year);

     @Query("SELECT new com.watches.backend.Dto.report.ReportDto(DAY(o.placedAt), COUNT(o)) " +
             "FROM Order o " +
             "WHERE YEAR(o.placedAt) = :year AND MONTH(o.placedAt) = :month " +
             "GROUP BY DAY(o.placedAt) " +
             "ORDER BY DAY(o.placedAt)")
     List<ReportDto<Integer>> countOrderDailyGroupedByDay(@Param("year") int year, @Param("month") String month);

}
