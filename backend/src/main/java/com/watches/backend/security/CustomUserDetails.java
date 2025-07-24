package com.watches.backend.security;

import com.watches.backend.model.User;
import com.watches.backend.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // hashed password from DB
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // using email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // or your logic
    }
     public User getUser(){
        return user;
    }
}
