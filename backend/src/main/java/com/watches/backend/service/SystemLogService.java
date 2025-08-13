package com.watches.backend.service;

import com.watches.backend.Repositories.SystemLogRepository;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.SystemLog;
import com.watches.backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class SystemLogService {

    private final SystemLogRepository repository;

    public void createAsync(String action, User performedBy, List<Object> param){
        SystemLog systemLog = new SystemLog(action, performedBy);
        repository.save(systemLog);
    }

    public CompletableFuture<List<SystemLog>> getAllAsync(){
        return CompletableFuture.completedFuture(repository.findAll());
    }

    public CompletableFuture<SystemLog> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(
                repository.findById(id)
                .orElseThrow(() -> CException.notFound(SystemLog.class, "id", id))
        );
    }

    public void deleteByIdAsync(Long id){
        CompletableFuture<SystemLog> systemLog = this.findByIdAsync(id);

        systemLog.thenAccept(
                repository::delete
        );
    }

}
