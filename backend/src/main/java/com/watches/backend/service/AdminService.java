package com.watches.backend.service;

import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.Dto.UpdateAdminPasswordDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.helpers.specification.SpecificationBuilder;
import com.watches.backend.helpers.query.UserQueryObject;
import com.watches.backend.helpers.Utils;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.Repositories.AdminRepository;
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
    public AdminDTO addRoleToAdmin(Long id, Role role) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> CException.notFound(Admin.class, "id", id));

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
                throw CException.badRequest(Admin.class, "Admin cannot have OWNER role");
            }
            roles.add(Role.ADMIN);
            admin.setRoles(roles);
        }
    }

}
