package com.bankapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankapp.dto.LoginRequestDto;
import com.bankapp.dto.LoginResponseDto;
import com.bankapp.entity.Employee;
import com.bankapp.exception.InvalidCredentialsException;
import com.bankapp.repository.EmployeeRepository;
import com.bankapp.util.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		System.out.println("Got request in service");
		Employee employee = employeeRepository.findByEmail(dto.getEmail());

		if (employee == null) {
			throw new InvalidCredentialsException("Email: " + dto.getEmail() + " doesn't exist in the database.");
		}

		if (!passwordEncoder.matches(dto.getPassword(), employee.getPassword())) {

			throw new InvalidCredentialsException("Wrong Password.");
		}
		
		String token = jwtUtil.generateToken(employee.getEmail(), employee.getRole());

		return new LoginResponseDto(
				token, employee.getEmail(), employee.getRole());
	}

}
