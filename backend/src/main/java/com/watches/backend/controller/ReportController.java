package com.watches.backend.controller;

import com.watches.backend.Dto.report.ReportDto;
import com.watches.backend.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/order/years")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getOrdersReport(@RequestParam(value = "year", required = false) String year){
        return service.getOrdersReport(year);
    }

    @GetMapping("/order/year")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getMonthlyOrdersReport(@RequestParam(value = "year") String year){
        return service.getOrderMonthlyReport(year);
    }

    @GetMapping("/order/month")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getDailyOrdersReport(@RequestParam(value = "year") String year,
                                       @RequestParam(value = "month") String month){
        return service.getDailyOrdersReport(year, month);
    }

    @GetMapping("/product/years")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getProductReport(@RequestParam(value = "year", required = false) String year){
        return service.getProductReport(year);
    }

    @GetMapping("/product/year")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getMonthlyProductReport(@RequestParam(value = "year") String year){
        return service.getMonthlyProductReport(year);
    }

    @GetMapping("/product/month")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<ReportDto<Integer>> getDailyProductReport(@RequestParam(value = "year") String year,
                                                   @RequestParam(value = "month") String month){
        return service.getDailyProductReport(year, month);
    }
}