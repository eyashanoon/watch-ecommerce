package com.watches.backend.service;

import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.Repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public AdminDTO createAdmin(CreateAdminDTO createAdminDTO) {
        Admin admin = AdminMapper.fromCreateDTO(createAdminDTO);
        Admin saved = adminRepository.save(admin);
        return AdminMapper.toDTO(saved);
    }
    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Admin not found with id:"+id));
        return AdminMapper.toDTO(admin);
    }
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream().map(AdminMapper::toDTO).collect(Collectors.toList());
     }
     public AdminDTO updateAdmin(Long id,UpdateAdminDTO updateAdminDTO) {
        Admin admin=adminRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Admin not found with id:"+id));
        AdminMapper.updateAdminFromDTO(updateAdminDTO,admin);
        Admin updated=adminRepository.save(admin);
        return AdminMapper.toDTO(updated);
     }
     public void deleteAdmin(Long id) {
        if(adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return;
        }
        else throw new EntityNotFoundException("Admin not found with id:"+id);

     }




}
