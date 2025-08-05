package com.watches.backend.controller;

import com.watches.backend.model.User;
import com.watches.backend.security.CustomUserDetails;
import com.watches.backend.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
          System.out.println(authRequest.getUsername()+" "+authRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.toList());

             String token = jwtUtil.generateToken(user.getEmail(), roles);

            return (new AuthResponse(token));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }



    @Getter
    @Setter
    @AllArgsConstructor
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}
