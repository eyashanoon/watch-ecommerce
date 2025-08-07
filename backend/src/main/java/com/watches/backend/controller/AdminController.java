package com.watches.backend.controller;

import com.watches.backend.Dto.*;
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.AdminQueryObject;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
        return adminService.createAdmin(createAdminDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN')")
    public AdminDTO getAdmin(@PathVariable Long id){
         return adminService.getAdminById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN')")
    public Page<AdminDTO> getAllAdmin(@RequestBody AdminQueryObject queryObject){
        Page<Admin> admins = adminService.getAllAdmins(queryObject).join();
        return admins.map(AdminMapper::toDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('UPDATE_ADMIN')")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminDTO updateAdminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdmin(id, updateAdminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('REMOVE_ADMIN')")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-role")
    @PreAuthorize("hasRole('OWNER') || hasRole('UPDATE_ADMIN')")
    public ResponseEntity<?> addRoleToAdmin(@RequestBody AddRoleDTO request) {
        try {
            Role role = request.getParsedRole();
            AdminDTO updatedAdmin = adminService.addRoleToAdmin(request.getUserId(), role);

            return ResponseEntity.ok(updatedAdmin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
