package com.watches.backend.Dto;

import com.watches.backend.enums.Role;

public class AddRoleDTO {
    private Long userId;
    private String role; // Accept role as a string

    public AddRoleDTO() {
    }

    public AddRoleDTO(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Parses the string role into the Role enum, throws exception if invalid.
     */
    public Role getParsedRole() {
        try {
            return Role.valueOf(role.toUpperCase()); // toUpperCase makes it case-insensitive
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role + ". Allowed roles: " + java.util.Arrays.toString(Role.values()));
        }
    }
}
