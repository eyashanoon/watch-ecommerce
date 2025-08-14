package com.watches.backend.Repositories;

import com.watches.backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaSpecificationExecutor<Admin>, JpaRepository<Admin, Long> {

}