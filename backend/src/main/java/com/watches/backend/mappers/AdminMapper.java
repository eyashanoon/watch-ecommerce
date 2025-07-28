package com.watches.backend.mappers;

import com.watches.backend.Dto.AdminDTO;
import com.watches.backend.Dto.CreateAdminDTO;
import com.watches.backend.Dto.UpdateAdminDTO;
import com.watches.backend.model.Admin;

public class AdminMapper {

    // Convert Admin entity to AdminDTO
    public static AdminDTO toDTO(Admin admin) {
        if (admin == null) return null;

        return new AdminDTO(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRoles().stream().map(role -> role.name()).toList()

        );
    }

    // Create new Admin entity from CreateAdminDTO
    public static Admin fromCreateDTO(CreateAdminDTO dto) {
        if (dto == null) return null;

        return new Admin(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhone()
        );
    }

    // Update existing Admin entity using UpdateAdminDTO
    public static void updateAdminFromDTO(UpdateAdminDTO dto, Admin admin) {
        if (dto == null || admin == null) return;

        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());
        admin.setPhone(dto.getPhone());
     }

}
