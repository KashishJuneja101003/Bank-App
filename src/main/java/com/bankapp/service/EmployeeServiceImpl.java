package com.bankapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bankapp.dto.EmployeeRequestDto;
import com.bankapp.dto.EmployeeResponseDto;
import com.bankapp.entity.Employee;
import com.bankapp.enums.Role;
import com.bankapp.repository.EmployeeRepository;
import com.bankapp.util.EmployeeDtoBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeDtoBuilder employeeDtoBuilder;
	
	@Override
	public EmployeeResponseDto addEmployee(EmployeeRequestDto dto) {
		System.out.println(dto.getRole());
		System.out.println(dto.getRole() == null);
		if(dto.getRole()==null) {
			dto.setRole(Role.CLERK);
		}
		
		System.out.println(dto.getRole());
		
		Employee employee = employeeRepository
				.save(employeeDtoBuilder.fromRequestDto(dto));
				
		return employeeDtoBuilder.toResponseDto(employee);
	}
	
	@Override
    public EmployeeResponseDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeDtoBuilder.toResponseDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeDtoBuilder::toResponseDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto updateEmployee(Integer id, EmployeeRequestDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());

        if (dto.getRole() != null) {
            employee.setRole(dto.getRole());
        }

        employee.setManagerId(dto.getManagerId());

        return employeeDtoBuilder.toResponseDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}
