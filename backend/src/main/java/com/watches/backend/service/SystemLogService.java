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

    public void createAsync(User performedBy, String request, String response) {
        SystemLog systemLog = new SystemLog();
        systemLog.setPerformedBy(performedBy);
        systemLog.setRequest(request);
        systemLog.setResponse(response);

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
}
