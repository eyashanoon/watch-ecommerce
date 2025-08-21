package com.watches.backend.service;

import com.watches.backend.Dto.report.IReport;
import com.watches.backend.Dto.report.DayReportDto;
import com.watches.backend.Dto.report.MonthReportDto;
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

    public List<IReport> getOrdersReport(String year){
        return orderRepository.countOrderGroupedByYear();
    }

    public List<IReport> getOrderMonthlyReport(String year){
        if(!Utils.isNullOrWhiteSpace(year)){
            List<IReport> res = orderRepository.countOrderMonthGroupedByMonth(year);
            validateMonthly(res);
            return res;
        }
        throw CException.badRequest(Order.class, "Invalid year");
    }

    public List<IReport> getDailyOrdersReport(String year, String month){
        if(!Utils.isNullOrWhiteSpace(year) || !Utils.isNullOrWhiteSpace(month)){
            List<IReport> res = orderRepository.countOrderDailyGroupedByDay(year, month);
            validateDaily(res, year, month);
            return res;
        }
        throw CException.badRequest(Order.class, "Invalid year or month");
    }

    public List<IReport> getProductReport(String feature, String year){
        int finalYear = Utils.isNullOrWhiteSpace(year) ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String finalFeature = Utils.isNullOrWhiteSpace(feature) ? "Brand" : Utils.normalizeString(feature);
        return productRepository.countProductGroupedByYear(finalFeature, finalYear);
    }

    private void validateMonthly(List<IReport> reports){
        Map<Integer, Long> map = new HashMap<>();
        for(int i = 1;i <= 12;i++){
            map.put(i, 0L);
        }
        for(IReport report : reports){
            MonthReportDto monthReportDto = (MonthReportDto) report;
            map.put(monthReportDto.getMonth(), monthReportDto.getCount());
        }
        reports.clear();
        for(Map.Entry<Integer, Long> entry : map.entrySet()){
            reports.add(new MonthReportDto(entry.getKey(), entry.getValue()));
        }
    }

    private void validateDaily(List<IReport> reports, String year, String month){
        Map<Integer, Long> map = new HashMap<>();
        for(int i = 1;i <= monthDays(year, month); i++){
            map.put(i, 0L);
        }
        for(IReport report : reports){
            DayReportDto dayReportDto = (DayReportDto) report;
            map.put(dayReportDto.getDay(), dayReportDto.getCount());
        }
        reports.clear();
        for(Map.Entry<Integer, Long> entry : map.entrySet()){
            reports.add(new DayReportDto(entry.getKey(), entry.getValue()));
        }
    }

    private int monthDays(String year, String month){
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

    private String validateMonth(String month){
        if(month.length() > 2){
            switch (month){
                case "January"   -> month = "01";
                case "February"  -> month = "02";
                case "March"     -> month = "03";
                case "April"     -> month = "04";
                case "May"       -> month = "05";
                case "June"      -> month = "06";
                case "July"      -> month = "07";
                case "August"    -> month = "08";
                case "September" -> month = "09";
                case "October"   -> month = "10";
                case "November"  -> month = "11";
                case "December"  -> month = "12";
                default -> throw CException.badRequest(Order.class, month + " is not a valid month");
            }
        }else{
            if(month.length() == 1){
                month = "0" + month;
            }
        }
        return month;
    }

}
