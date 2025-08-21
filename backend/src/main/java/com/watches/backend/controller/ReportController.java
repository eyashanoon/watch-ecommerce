package com.watches.backend.controller;

import com.watches.backend.Dto.report.IReport;
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
    List<IReport> getOrdersReport(@RequestParam(value = "year", required = false) String year){
        return service.getOrdersReport(year);
    }

    @GetMapping("/order/year")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<IReport> getMonthlyOrdersReport(@RequestParam(value = "year") String year){
        return service.getOrderMonthlyReport(year);
    }

    @GetMapping("/order/month")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<IReport> getDailyOrdersReport(@RequestParam(value = "year") String year,
                                       @RequestParam(value = "month") String month){
        return service.getDailyOrdersReport(year, month);
    }

    @GetMapping("/product/{feature}/years")
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN')")
    List<IReport> getProductReport(@PathVariable String feature, @RequestParam(value = "year", required = false) String year){
        return service.getProductReport(feature, year);
    }

}
