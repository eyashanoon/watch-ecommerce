package com.watches.backend.controller;

import com.watches.backend.model.SystemLog;
import com.watches.backend.service.SystemLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
public class SystemLogController {

    private final SystemLogService service;

    @GetMapping
    List<SystemLog> getAll(){
        return service.getAllAsync().join();
    }

    @GetMapping("/{id}")
    SystemLog getById(@PathVariable Long id){
        return service.findByIdAsync(id).join();
    }
}
