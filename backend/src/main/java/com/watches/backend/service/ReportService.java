package com.watches.backend.service;

import com.watches.backend.Dto.report.ReportDto;
import com.watches.backend.Repositories.OrderRepository;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<ReportDto<Integer>> getOrdersReport(String year){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        return orderRepository.countOrderGroupedByYear(finalYear);
    }

    public List<ReportDto<Integer>> getOrderMonthlyReport(String year){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        List<ReportDto<Integer>> res = orderRepository.countOrderMonthGroupedByMonth(finalYear);
        validateMonthly(res);
        return res;

    }

    public List<ReportDto<Integer>> getDailyOrdersReport(String year, String month){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String finalMonth = Utils.isNullOrWhiteSpace(month) ? LocalDateTime.now().getMonth().toString() : month;
        List<ReportDto<Integer>> res = orderRepository.countOrderDailyGroupedByDay(finalYear, finalMonth);
        validateDaily(res, year, month);
        return res;
    }

    public List<ReportDto<Integer>> getProductReport(String year){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        return productRepository.countProductGroupedByYear(finalYear);
    }

    public List<ReportDto<Integer>> getMonthlyProductReport(String year){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        List<ReportDto<Integer>> res = productRepository.countMonthlyProductGroupedByMonth(finalYear);
        validateMonthly(res);
        return res;
    }

    public List<ReportDto<Integer>> getDailyProductReport(String year, String month){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String finalMonth = Utils.isNullOrWhiteSpace(month) ? LocalDateTime.now().getMonth().toString() : month;
        List<ReportDto<Integer>> res = productRepository.countDailyProductGroupedByDay(finalYear, finalMonth);
        validateDaily(res, year, month);
        return res;
    }

    private void validateMonthly(List<ReportDto<Integer>> reports){
        Map<Integer, Long> map = new HashMap<>();
        for(int i = 1;i <= 12;i++){
            map.put(i, 0L);
        }
        finalReports(map, reports);
    }

    private void validateDaily(List<ReportDto<Integer>> reports, String year, String month){
        Map<Integer, Long> map = new HashMap<>();
        for(int i = 1;i <= monthDays(year, month); i++){
            map.put(i, 0L);
        }
        finalReports(map, reports);
    }

    private void finalReports(Map<Integer, Long> map, List<ReportDto<Integer>> reports){
        for(ReportDto<Integer> report : reports){
            map.put(report.getField(), report.getCount());
        }
        reports.clear();
        for(Map.Entry<Integer, Long> entry : map.entrySet()){
            reports.add(new ReportDto<>(entry.getKey(), entry.getValue()));
        }
    }

    private int monthDays(String year, String month){
        if(month.length() == 1) month = "0" + month;
        switch (month){
            case "01":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                return 31;
            case "03":
            case "04":
            case "06":
            case "09":
            case "11":
                return 30;
            case "02":
                int intYear = Integer.parseInt(year);
                if(intYear % 400 == 0 || (intYear % 4 == 0 && intYear % 100 != 0)) return 29;
                return 28;
        }
        throw CException.badRequest(Order.class, "Invalid month");
    }

}
