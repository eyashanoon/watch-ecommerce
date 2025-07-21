package com.watches.backend.Repositories;

import com.watches.backend.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 
@Repository  // optional but good practice
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
 }
