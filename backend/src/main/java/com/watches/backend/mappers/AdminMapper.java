package com.watches.backend.mappers;

import com.watches.backend.Dto.admin.AdminDTO;
import com.watches.backend.Dto.admin.CreateAdminDTO;
import com.watches.backend.Dto.admin.UpdateAdminPasswordDTO;
import com.watches.backend.model.Admin;

public class AdminMapper {
    public static AdminDTO toDTO(Admin admin) {
        if (admin == null) return null;
        return new AdminDTO(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRoles().stream().map(Enum::name).toList()

        );
    }
    public static Admin fromCreateDTO(CreateAdminDTO dto) {
        if (dto == null) return null;

        return new Admin(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhone()
        );
    }
    public static void updatePassAdminFromDTO(UpdateAdminPasswordDTO dto, Admin admin) {
        if (dto == null || admin == null) return;
        admin.setPassword(dto.getPassword());

    }
}
