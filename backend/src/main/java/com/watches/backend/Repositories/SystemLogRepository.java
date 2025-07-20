package com.watches.backend.Repositories;

import com.watches.backend.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLog,Long> {
}
