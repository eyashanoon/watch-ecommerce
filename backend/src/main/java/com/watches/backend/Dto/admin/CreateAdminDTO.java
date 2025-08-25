package com.watches.backend.Dto.admin;

import com.watches.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminDTO {
    private String username;
    private String email;
    private String password;
    private String phone;
    private Set<Role> roles;
}
