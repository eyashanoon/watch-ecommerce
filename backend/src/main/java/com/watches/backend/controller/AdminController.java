package com.watches.backend.controller;

import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    public final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @PostMapping
    public ResponseEntity<AdminDTO> CreateAdmin(@RequestBody CreateAdminDTO  createAdminDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(createAdminDTO) );
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id){
         return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminById(id));
    }
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmin(){
         return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins());
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
}
