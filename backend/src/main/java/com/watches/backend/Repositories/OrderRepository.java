package com.watches.backend.Repositories;

import com.watches.backend.Dto.report.IReport;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
     Optional<Order> findByCustomer(Customer customer);

     @Query("SELECT new com.watches.backend.Dto.report.YearReportDto(YEAR(o.placedAt), COUNT(o)) " +
             "FROM Order o " +
             "GROUP BY YEAR(o.placedAt)" +
             "ORDER BY YEAR(o.placedAt)")
     List<IReport> countOrderGroupedByYear();

     @Query("SELECT new com.watches.backend.Dto.report.MonthReportDto(MONTH(o.placedAt), COUNT(o)) " +
             "FROM Order o " +
             "WHERE YEAR(o.placedAt) = :year " +
             "GROUP BY MONTH(o.placedAt) " +
             "ORDER BY MONTH(o.placedAt)")
     List<IReport> countOrderMonthGroupedByMonth(@Param("year") String year);

     @Query("SELECT new com.watches.backend.Dto.report.DayReportDto(DAY(o.placedAt), COUNT(o)) " +
             "FROM Order o " +
             "WHERE YEAR(o.placedAt) = :year AND MONTH(o.placedAt) = :month " +
             "GROUP BY DAY(o.placedAt) " +
             "ORDER BY DAY(o.placedAt)")
     List<IReport> countOrderDailyGroupedByDay(@Param("year") String year, @Param("month") String month);

}
