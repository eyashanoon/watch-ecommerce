package com.watches.backend.controller;

import com.watches.backend.model.SystemLog;
import com.watches.backend.service.SystemLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
public class SystemLogController {

    private final SystemLogService service;

    @GetMapping
    CompletableFuture<ResponseEntity<List<SystemLog>>> getAll(){

        CompletableFuture<List<SystemLog>> systemLogs = service.getAllAsync();

        return systemLogs.thenApply(sl ->
                ResponseEntity.ok()
                        .body(sl)
        );

    }

    @GetMapping("/{id}")
    CompletableFuture<ResponseEntity<SystemLog>> getById(@PathVariable Long id){

        CompletableFuture<SystemLog> systemLog = service.findByIdAsync(id);

        return systemLog.thenApply(sl ->
                ResponseEntity.ok()
                        .body(sl)
        );
    }
}
