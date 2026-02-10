package com.bankapp.dto;

import com.bankapp.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEmployeeRequestDto {

    private String name;
    private String email;

    // "CLERK" or "MANAGER"
    private Role role;

    // only for clerk
    private Integer managerId;
    
    private String password;
}
