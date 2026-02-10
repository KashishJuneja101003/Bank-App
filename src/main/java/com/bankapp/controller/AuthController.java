package com.bankapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.LoginRequestDto;
import com.bankapp.dto.LoginResponseDto;
import com.bankapp.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/employees/auth")
public class AuthController {
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
		System.out.println("Got request in controller");
		LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
		
		return ResponseEntity.ok(loginResponseDto);
	}
}
