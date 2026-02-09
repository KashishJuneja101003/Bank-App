package com.bankapp.util;

import org.springframework.stereotype.Component;

import com.bankapp.dto.EmployeeRequestDto;
import com.bankapp.dto.EmployeeResponseDto;
import com.bankapp.entity.Employee;

@Component
public class EmployeeDtoBuilder {
	public Employee fromRequestDto(EmployeeRequestDto dto) {
		return Employee.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.managerId(dto.getManagerId())
				.role(dto.getRole())
				.build();
	}
	
	public EmployeeResponseDto toResponseDto(Employee employee) {
		return EmployeeResponseDto.builder()
				.employeeId(employee.getEmployeeId())
				.name(employee.getName())
				.email(employee.getEmail())
				.role(employee.getRole())
				.managerId(employee.getManagerId())
				.build();
	}

}
