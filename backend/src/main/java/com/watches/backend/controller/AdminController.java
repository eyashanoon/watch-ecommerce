package com.watches.backend.controller;

import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.Dto.AddRoleDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
 @RestController
@RequestMapping("/api/admins")
public class AdminController {
    public final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AdminDTO> CreateAdmin(@RequestBody CreateAdminDTO  createAdminDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(createAdminDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id){
         return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminById(id));
    }

    @PreAuthorize("hasRole('ADD_ADMINS')")
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmin(){
         return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins());
    }
     @GetMapping("/without-loggedin")
     public ResponseEntity<List<AdminDTO>> getAllAdminWithoutTheSignedInAdmin(){
         return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdminsWithoutTheSingedInAdmin());
     }
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminDTO updateAdminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdmin(id, updateAdminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/add-role")
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
