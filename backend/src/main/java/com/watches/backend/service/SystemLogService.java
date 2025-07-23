package com.watches.backend.service;

import com.watches.backend.Dto.SystemLogDto.CreateSystemLogDto;
import com.watches.backend.Repositories.SystemLogRepository;
import com.watches.backend.exceptions.SystemLogNotFoundException;
import com.watches.backend.mappers.SystemLogMapper;
import com.watches.backend.model.SystemLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService {

    private final SystemLogRepository repository;

    public SystemLogService(SystemLogRepository repository) {
        this.repository = repository;
    }

    public SystemLog create(CreateSystemLogDto createSystemLogDto){
        SystemLog systemLog = SystemLogMapper.createToSystemLog(createSystemLogDto);
        repository.save(systemLog);
        return systemLog;
    }

    public List<SystemLog> getAll(){
        return repository.findAll();
    }

    public SystemLog getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new SystemLogNotFoundException(id));
    }

    public SystemLog deleteById(Long id){
        SystemLog systemlog = this.getById(id);
        repository.delete(systemlog);
        return systemlog;
    }

}
