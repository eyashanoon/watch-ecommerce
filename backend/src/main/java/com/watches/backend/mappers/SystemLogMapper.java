package com.watches.backend.mappers;

import com.watches.backend.Dto.systemLog.SystemLogDto;
import com.watches.backend.model.SystemLog;

public class SystemLogMapper {
    public static SystemLogDto toDto(SystemLog log){
        return new SystemLogDto(
                log.getPerformedBy().getEmail(),
                log.getRequest(),
                log.getResponse()
        );
    }
}
