package com.bankapp.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.bankapp.entity.Employee;
import com.bankapp.enums.Role;
import com.bankapp.repository.EmployeeRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtUtil {
	private final EmployeeRepository employeeRepository;
	
    private static final String SECRET_KEY =
            "bankapp_super_secret_key_which_is_very_long";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, Role role) {

    	Employee employee = employeeRepository.findByEmail(email);
    	
        return Jwts.builder()
                .setSubject(email)
                .claim("role", employee.getRole())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
	