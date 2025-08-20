package com.watches.backend.Repositories;

import com.watches.backend.model.Admin;
import com.watches.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaSpecificationExecutor<Admin>, JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(@Param("email") String email);
}