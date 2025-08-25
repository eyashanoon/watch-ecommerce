package com.watches.backend.security;

import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration-time}")
    private long EXPIRATION_TIME;

    private final UserService userService;

    public JwtUtil(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(String username, List<String> roles) {
        if(username == null || roles == null){
            throw CException.badRequest(Authentication.class, "username and roles cannot be null");
        }
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 1 year
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        if(token == null){
            throw CException.badRequest(Authentication.class, "token cannot be null");
        }
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    private Claims extractAllClaims(String token) {
        if(token == null){
            throw CException.badRequest(Authentication.class, "token cannot be null");
        }
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String username) {
        if(token==null || Utils.isNullOrEmpty(username)){
            throw CException.badRequest(Authentication.class, "token and username cannot be null");
        }
        String tokenUsername = extractUsername(token);
        List<String> roles = extractRoles(token);
        boolean validRoles = userService.checkRoles(username, roles);
        return (tokenUsername.equals(username) && !isTokenExpired(token)) && validRoles;
    }

    public boolean validateToken(String token){
        try {
            Key key = getSigningKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }



}
