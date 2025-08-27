package com.watches.backend.controller;

import com.watches.backend.Dto.admin.AdminDTO;
import com.watches.backend.Dto.admin.CreateAdminDTO;
import com.watches.backend.Dto.admin.UpdateAdminDTO;
import com.watches.backend.Dto.admin.UpdateAdminPasswordDTO;
import com.watches.backend.Dto.customer.UpdateCustomerDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.query.UserQueryObject;
import com.watches.backend.mappers.AdminMapper;
import com.watches.backend.mappers.CustomerMapper;
import com.watches.backend.model.Admin;
import com.watches.backend.model.Customer;
import com.watches.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    public final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('ADD_ADMIN')")
    public AdminDTO CreateAdmin(@RequestBody CreateAdminDTO createAdminDTO){
        Admin admin = adminService.createAdmin(createAdminDTO);
        return AdminMapper.toDTO(admin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN') || hasRole('UPDATE_ADMIN') ||  hasRole('REMOVE_ADMIN')")
    public AdminDTO getAdmin(@PathVariable Long id){
         Admin admin = adminService.findById(id);
         return AdminMapper.toDTO(admin);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_ADMIN') || hasRole('UPDATE_ADMIN') || hasRole('REMOVE_ADMIN')")
    public Page<AdminDTO> getAllAdmin(@ModelAttribute UserQueryObject queryObject) {
        Page<Admin> admins = adminService.getAllAdmins(queryObject);
        return admins.map(AdminMapper::toDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDTO updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminDTO updateAdminDTO) {
        Admin admin = adminService.updateAdmin(id, updateAdminDTO);
        return AdminMapper.toDTO(admin);
    }
    @PutMapping("/changePassword/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    public AdminDTO updateAdminPassword(@PathVariable Long id, @RequestBody UpdateAdminPasswordDTO updateAdminPasswordDTO) {
        return adminService.updateAdminPassword(id, updateAdminPasswordDTO);
    }

    @PutMapping("/gg")
    AdminDTO updateAdmin2(@RequestBody UpdateCustomerDTO updateCustomerDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Admin admin = adminService.updateAdmin2(username, updateCustomerDTO).join();
        return AdminMapper.toDTO(admin);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('REMOVE_ADMIN')")
    public void deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('OWNER')   || hasRole('UPDATE_ADMIN')  ")
    public List<String> getAllRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }
}
