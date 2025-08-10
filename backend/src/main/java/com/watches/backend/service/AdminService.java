package com.watches.backend.service;

import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.AdminQueryObject;
import com.watches.backend.helpers.AdminSpecificationBuilder;
import com.watches.backend.helpers.Utils;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.Repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin createAdmin(CreateAdminDTO createAdminDTO) {
        Admin admin = AdminMapper.fromCreateDTO(createAdminDTO);

        setPassword(admin);
        validateAndSetRoles(admin, createAdminDTO.getRoles());

        return adminRepository.save(admin);
    }

    public Page<Admin> getAllAdmins(AdminQueryObject queryObject) {

        Specification<Admin> spec = new AdminSpecificationBuilder()
                .withFilter(queryObject)
                .build();

        return adminRepository.findAll(spec,
                PageRequest.of(queryObject.getPageNumber() - 1, queryObject.getPageSize())
        );
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }

    public Admin updateAdmin(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = findById(id);

        admin.setUsername(updateAdminDTO.getUsername());
        admin.setEmail(updateAdminDTO.getEmail());
        admin.setPhone(updateAdminDTO.getPhone());
        validateAndSetRoles(admin, updateAdminDTO.getRoles());

        return adminRepository.save(admin);
    }

    public AdminDTO updateAdminPassword(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = findById(id);
        if(!Utils.isNullOrEmpty(updateAdminDTO.getRoles())) {
            if(admin.getRoles()==null) {
                admin.setRoles(new HashSet<>());
            }
            Set<Role> roles = updateAdminDTO.getRoles();
            admin.setRoles(roles);
        }
        AdminMapper.updateAdminFromDTO(updateAdminDTO, admin);
        Admin updated = adminRepository.save(admin);
        return AdminMapper.toDTO(updated);
    }

    public void deleteAdmin(Long id) {
        Admin admin = findById(id);
        admin.setDeleted(true);
        adminRepository.save(admin);
    }
    public AdminDTO addRoleToAdmin(Long id, Role role) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));

        // Ensure the roles collection is initialized
        if (admin.getRoles() == null) {
            admin.setRoles(new HashSet<>()); // or new ArrayList<> depending on your design
        }

        if (admin.getRoles().add(role)) {
            // Only save if the role was actually added (Set.add returns true if added)
            admin = adminRepository.save(admin);
            System.out.println(admin.getRoles());
        }

        return AdminMapper.toDTO(admin);
    }

    private void setPassword(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    }

    private void validateAndSetRoles(Admin admin, Set<Role> roles){
        if(!Utils.isNullOrEmpty(roles)) {
            if(roles.contains(Role.OWNER)) {
                throw new RuntimeException("Admins cannot have OWNER role");
            }
            roles.add(Role.ADMIN);
            admin.setRoles(roles);
        }
    }

}
