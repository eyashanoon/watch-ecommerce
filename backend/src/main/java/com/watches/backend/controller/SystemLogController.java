package com.watches.backend.controller;

import com.watches.backend.Dto.SystemLogDto.SystemLogDto;
import com.watches.backend.helpers.query.SystemlogQueryObject;
import com.watches.backend.mappers.SystemLogMapper;
import com.watches.backend.model.SystemLog;
import com.watches.backend.service.SystemLogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
public class SystemLogController {

    private final SystemLogService service;

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    Page<SystemLogDto> getAll(@Valid @ModelAttribute SystemlogQueryObject query) {
        Page<SystemLog> logs = service.getAllAsync(query).join();
        return logs.map(SystemLogMapper::toDto);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('OWNER')")
    Set<String> getAllStatus(){
        return service.getStatus().join();
    }

}
