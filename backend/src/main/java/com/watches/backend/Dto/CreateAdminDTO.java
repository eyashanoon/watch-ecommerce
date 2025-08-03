package com.watches.backend.Dto;

import com.watches.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class CreateAdminDTO {
    private String userName;
    private String email;
    private String password;
    private String phone;
    private List<Role> roles;


    public CreateAdminDTO() {}

    public CreateAdminDTO(String username, String email, String password, String phone, List<Role> roles) {
        this.userName = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.roles = roles;
    }

    // Getters
    public String getUsername() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.userName = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
