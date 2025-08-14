package com.watches.backend.Dto;

import java.util.List;
public class AdminDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;

    public AdminDTO() {}

    public AdminDTO(Long id, String username, String email, String phone, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<String> getRoles() { return roles; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    @Override
    public String toString() {
        return "AdminDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                '}';
    }
}
