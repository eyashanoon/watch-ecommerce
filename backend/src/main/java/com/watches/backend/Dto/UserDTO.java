package com.watches.backend.Dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String phone, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
