package com.bankapp.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bankapp.entity.Employee;
import com.bankapp.enums.Role;
import com.bankapp.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor

public class SecurityUtil {
	EmployeeRepository employeeRepository;
	

    public Integer getLoggedInUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
        	System.out.println("❌ NO OR INVALID AUTH HEADER");
            throw new RuntimeException("No authenticated user found");
        }
        
        String email = authentication.getName(); // EMAIL from JWT

        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            throw new RuntimeException("Employee not found for email: " + email);
        }

        return employee.getEmployeeId();
    }
    
    public Role getLoggedInUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(roleStr -> Role.valueOf(roleStr.replace("ROLE_", "")))
                .findFirst()
                .orElse(null);
    }
}
