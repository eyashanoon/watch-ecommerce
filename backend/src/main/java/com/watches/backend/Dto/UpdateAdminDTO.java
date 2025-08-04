package com.watches.backend.Dto;

import com.watches.backend.enums.Role;

import java.util.List;
import java.util.Set;

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

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
