package com.watches.backend.security;

import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.User;
import com.watches.backend.service.SystemLogService;
import com.watches.backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.print.DocFlavor;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final SystemLogService systemLogService;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            if(!jwtUtil.validateToken(jwt)){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                return;
            }
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                throw CException.unauthorized("Invalid Token");
            }
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                List<String> roles = jwtUtil.extractRoles(jwt);
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> "ROLE_" + role)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
        if(!Objects.equals(request.getMethod(), "GET")) {
            if(username != null) {
                User user = userService.findByUsername(username);
                systemLogService.createAsync(user, getData(request), getData(response));
            }
        }
    }

    private String getData(HttpServletRequest request) {

        return "RequestId: " + UUID.randomUUID() +
                ",Method: " + request.getMethod() +
                ",URI: " + request.getRequestURI() +
                ",Query String: " + request.getQueryString() +
                ",Remote Address: " + request.getRemoteAddr() +
                ",User Agent: " + request.getHeader("User-Agent");
    }

    private String getData(HttpServletResponse response) {

        return "Response Status: " + response.getStatus() +
                ",Response Message: " + HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
    }

}
