package com.watches.backend.service;

import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.service.AuthService;
import com.watches.backend.Repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    public AdminDTO createAdmin(CreateAdminDTO createAdminDTO) {
        Admin admin = AdminMapper.fromCreateDTO(createAdminDTO);

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        if( createAdminDTO.getRoles()!=null && !createAdminDTO.getRoles().isEmpty()) {
            if(admin.getRoles()==null) {
                admin.setRoles(new HashSet<>());
             }
            Set<Role> roles = createAdminDTO.getRoles();
           roles.add(Role.ADMIN);
            admin.setRoles(roles);
        }
        Admin saved = adminRepository.save(admin);
        return AdminMapper.toDTO(saved);
    }

    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
        return AdminMapper.toDTO(admin);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AdminDTO> getAllAdminsWithoutTheSingedInAdmin() {
        Long adminId = authService.getCurrentUserId();
        return adminRepository.findAll()
                .stream()
                .map(AdminMapper::toDTO)
                .toList().stream().filter(admin -> !admin.getId().equals(adminId)).collect(Collectors.toList());
    }


    public AdminDTO updateAdmin(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
        if( updateAdminDTO.getRoles() !=null && !updateAdminDTO.getRoles().isEmpty()) {
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
    public AdminDTO updateAdminPassword(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
        if( updateAdminDTO.getRoles() !=null && !updateAdminDTO.getRoles().isEmpty()) {
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
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
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

}
