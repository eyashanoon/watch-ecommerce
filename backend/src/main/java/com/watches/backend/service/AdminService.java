package com.watches.backend.service;

import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.Repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminDTO createAdmin(CreateAdminDTO createAdminDTO) {
        Admin admin = AdminMapper.fromCreateDTO(createAdminDTO);

        // ✅ Encode password before saving
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

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

    public AdminDTO updateAdmin(Long id, UpdateAdminDTO updateAdminDTO) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));

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
            admin.setRoles(new ArrayList<>()); // or new ArrayList<> depending on your design
        }

        if (admin.getRoles().add(role)) {
            // Only save if the role was actually added (Set.add returns true if added)
            admin = adminRepository.save(admin);
            System.out.println(admin.getRoles());
        }

        return AdminMapper.toDTO(admin);
    }

}
