package com.watches.backend.controller;

import com.watches.backend.Dto.SystemLogDto.CreateSystemLogDto;
import com.watches.backend.model.SystemLog;
import com.watches.backend.service.SystemLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class SystemLogController {

    private final SystemLogService service;

    public SystemLogController(SystemLogService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<SystemLog>> GetAll(){
        List<SystemLog> logs = service.getAll();
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping("/{id}")
    ResponseEntity<SystemLog> GetById(@PathVariable Long id){
         SystemLog systemlog = service.getById(id);
         return ResponseEntity.ok().body(systemlog);
    }

    @PostMapping
    ResponseEntity<SystemLog> Create(@Valid @RequestBody CreateSystemLogDto systemLogDto){
        SystemLog systemlog = service.create(systemLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(systemlog);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SystemLog> Delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
