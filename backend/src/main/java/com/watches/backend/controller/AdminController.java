package com.watches.backend.controller;

import com.watches.backend.Dto.*;
import com.watches.backend.helpers.AdminQueryObject;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    public final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('ADD_ADMIN')")
    public AdminDTO CreateAdmin(@RequestBody CreateAdminDTO  createAdminDTO){
        Admin admin = adminService.createAdmin(createAdminDTO);
        return AdminMapper.toDTO(admin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN')")
    public AdminDTO getAdmin(@PathVariable Long id){
         Admin admin = adminService.findById(id);
         return AdminMapper.toDTO(admin);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN')")
    public Page<AdminDTO> getAllAdmin(@RequestBody AdminQueryObject queryObject){
        Page<Admin> admins = adminService.getAllAdmins(queryObject);
        return admins.map(AdminMapper::toDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('UPDATE_ADMIN')")
    public AdminDTO updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminDTO updateAdminDTO) {
        Admin admin = adminService.updateAdmin(id, updateAdminDTO);
        return AdminMapper.toDTO(admin);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('REMOVE_ADMIN')")
    public void deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
    }
}
