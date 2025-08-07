package com.watches.backend.Dto;

import com.watches.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UpdateAdminDTO {
    private String username;
    private String email;
    private String phone;
    private Set<Role> roles;

    public UpdateAdminDTO() {}

    public UpdateAdminDTO(String username, String email, String phone, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }
 
}
