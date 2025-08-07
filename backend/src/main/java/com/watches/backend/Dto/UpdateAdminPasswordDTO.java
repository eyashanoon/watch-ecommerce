package com.watches.backend.Dto;

public class UpdateAdminPasswordDTO {
    private String password;


    public UpdateAdminPasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
