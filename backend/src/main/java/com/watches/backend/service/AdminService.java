package com.watches.backend.service;

 import com.watches.backend.Dto.*;
 
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.helpers.specification.SpecificationBuilder;
import com.watches.backend.helpers.query.UserQueryObject;
import com.watches.backend.helpers.Utils;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.Repositories.AdminRepository;
import com.watches.backend.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin createAdmin(CreateAdminDTO createAdminDTO) {
        Admin admin = adminRepository.findByEmail(createAdminDTO.getEmail());
        if(admin != null){
            admin.setDeleted(false);
            admin.setPassword(createAdminDTO.getPassword());
        }else {
            admin = AdminMapper.fromCreateDTO(createAdminDTO);
        }
        setPassword(admin);
        validateAndSetRoles(admin, createAdminDTO.getRoles());
        return adminRepository.save(admin);
    }

    public Page<Admin> getAllAdmins(UserQueryObject queryObject) {

        Specification<Admin> spec = new SpecificationBuilder<>(Admin.class)
                .withFilter(queryObject)
                .build();

        return adminRepository.findAll(spec,
                PageRequest.of(queryObject.getPageNumber() - 1, queryObject.getPageSize())
        );
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> CException.notFound(Admin.class, "id", id));
    }

    public Admin updateAdmin(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = findById(id);

        admin.setUsername(updateAdminDTO.getUsername());
        admin.setEmail(updateAdminDTO.getEmail());
        admin.setPhone(updateAdminDTO.getPhone());
        validateAndSetRoles(admin, updateAdminDTO.getRoles());
        return adminRepository.save(admin);
    }

    public CompletableFuture<Admin> updateAdmin2(String username, UpdateCustomerDTO updateCustomerDTO) {
        Admin admin = adminRepository.findByEmail(username);

        admin.setUsername(updateCustomerDTO.getUsername());
        admin.setEmail(username);
        admin.setPhone(updateCustomerDTO.getPhone());
        if(updateCustomerDTO.getNewPassword() != null && !updateCustomerDTO.getNewPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(updateCustomerDTO.getNewPassword()));
        }
        return CompletableFuture.completedFuture(adminRepository.save(admin));
    }
    public AdminDTO updateAdminPassword(Long id, UpdateAdminPasswordDTO updateAdminPasswordDTO) {
        Admin admin = findById(id);


        AdminMapper.updatePassAdminFromDTO(updateAdminPasswordDTO, admin);
        setPassword(admin);
        Admin updated = adminRepository.save(admin);

        return AdminMapper.toDTO(updated);
    }

    public void deleteAdmin(Long id) {
        Admin admin = findById(id);
        admin.setDeleted(true);
        adminRepository.save(admin);
    }

    private void setPassword(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    }

    private void validateAndSetRoles(Admin admin, Set<Role> roles){
        if(!Utils.isNullOrEmpty(roles)) {
           /* if(roles.contains(Role.OWNER)) {
                throw CException.badRequest(Admin.class, "Admin cannot have OWNER role");
            }*/
            roles.add(Role.ADMIN);
            admin.setRoles(roles);
        }
    }

}
