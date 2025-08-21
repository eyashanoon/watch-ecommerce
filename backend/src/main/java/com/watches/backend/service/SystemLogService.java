package com.watches.backend.service;

import com.watches.backend.Repositories.SystemLogRepository;
import com.watches.backend.helpers.query.SystemlogQueryObject;
import com.watches.backend.helpers.specification.SpecificationBuilder;
import com.watches.backend.model.SystemLog;
import com.watches.backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public CompletableFuture<Page<SystemLog>> getAllAsync(SystemlogQueryObject query){
        Specification<SystemLog> spec = new SpecificationBuilder<>(SystemLog.class)
                .withFilter(query)
                .build();
        Page<SystemLog> res =  repository.findAll(spec,
                PageRequest.of(query.getPage() - 1, query.getPageSize())
        );
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<Set<String>> getStatus(){
        List<SystemLog> logs = repository.findAll();
        Set<String> res = new HashSet<>();
        for(SystemLog systemLog : logs){
            String response = systemLog.getResponse();
            res.add(response.substring(17,20));
        }
        return CompletableFuture.completedFuture(res);
    }



}
