package com.bankapp.dto;

import com.bankapp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseDto {
	private Integer employeeId;
	private String name;
	private String email;
	private Role role;
	private Integer managerId;
}
