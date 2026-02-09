package com.bankapp.dto;

import com.bankapp.enums.Role;

import lombok.Data;

@Data
public class EmployeeRequestDto {

    private String name;
    private String email;

    // "CLERK" or "MANAGER"
    private Role role;

    // only for clerk
    private Integer managerId;
}
