package com.watches.backend.Dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
public class AdminDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;

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
