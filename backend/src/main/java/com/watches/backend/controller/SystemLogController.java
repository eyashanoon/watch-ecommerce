package com.watches.backend.controller;

import com.watches.backend.Dto.SystemLogDto.CreateSystemLogDto;
import com.watches.backend.model.SystemLog;
import com.watches.backend.service.SystemLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/logs")
@Async
public class SystemLogController {

    private final SystemLogService service;

    public SystemLogController(SystemLogService service) {
        this.service = service;
    }

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

    @PostMapping
    CompletableFuture<ResponseEntity<SystemLog>> create(@Valid @RequestBody CreateSystemLogDto systemLogDto){
        CompletableFuture<SystemLog> systemLog = service.createAsync(systemLogDto);

        return systemLog.thenApply(sl ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(sl)
        );

    }

    @DeleteMapping("/{id}")
    CompletableFuture<ResponseEntity<SystemLog>> delete(@PathVariable Long id){
        service.deleteByIdAsync(id);
        return CompletableFuture.completedFuture(
                ResponseEntity.noContent()
                        .build()
        );
    }

}
