package com.watches.backend.service;

import com.watches.backend.Dto.SystemLogDto.CreateSystemLogDto;
import com.watches.backend.Repositories.SystemLogRepository;
import com.watches.backend.exceptions.SystemLogNotFoundException;
import com.watches.backend.mappers.SystemLogMapper;
import com.watches.backend.model.SystemLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class SystemLogService {

    private final SystemLogRepository repository;

    public SystemLogService(SystemLogRepository repository) {
        this.repository = repository;
    }

//    public CompletableFuture<SystemLog> createAsync(CreateSystemLogDto createSystemLogDto){
//        SystemLog systemLog = SystemLogMapper.createToSystemLog(createSystemLogDto);
//        repository.save(systemLog);
//        return CompletableFuture.completedFuture(systemLog);
//    }

    public CompletableFuture<List<SystemLog>> getAllAsync(){
        return CompletableFuture.completedFuture(repository.findAll());
    }

    public CompletableFuture<SystemLog> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(
                repository.findById(id)
                .orElseThrow(() ->
                        new SystemLogNotFoundException(id)
                )
        );
    }

    public void deleteByIdAsync(Long id){
        CompletableFuture<SystemLog> systemLog = this.findByIdAsync(id);

        systemLog.thenAccept(
                repository::delete
        );
    }

}
