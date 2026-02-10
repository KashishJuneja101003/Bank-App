package com.bankapp.service;

import java.util.List;

import com.bankapp.dto.CreateEmployeeRequestDto;
import com.bankapp.dto.EmployeeRequestDto;
import com.bankapp.dto.EmployeeResponseDto;

public interface EmployeeService {

    EmployeeResponseDto addEmployee(CreateEmployeeRequestDto dto);

    EmployeeResponseDto getEmployeeById(Integer id);

    List<EmployeeResponseDto> getAllEmployees();

    EmployeeResponseDto updateEmployee(Integer id, EmployeeRequestDto dto);

    void deleteEmployee(Integer id);
}

