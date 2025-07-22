package com.watches.backend.controller;

import com.watches.backend.Dto.SystemLogDto.CreateSystemLogDto;
import com.watches.backend.Repositories.SystemLogRepository;
import com.watches.backend.exceptions.SystemLogNotFoundException;
import com.watches.backend.mappers.SystemLogMapper;
import com.watches.backend.model.SystemLog;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SystemLogController {

    private final SystemLogRepository repository;

    public SystemLogController(SystemLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/logs")
    List<SystemLog> GetAll(){
        return repository.findAll();
    }

    @GetMapping("/logs/{id}")
    SystemLog GetById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new SystemLogNotFoundException(id));
    }

    @PostMapping("/logs")
    SystemLog Create(@Valid @RequestBody CreateSystemLogDto systemLogDto){
        return repository.save(SystemLogMapper.createToSystemLog(systemLogDto));
    }

    @DeleteMapping("/logs/{id}")
    void Delete(@PathVariable Long id){
        repository.deleteById(id);
    }

}
