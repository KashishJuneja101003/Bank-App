package com.bankapp.dto;

import com.bankapp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
	private String token;
	private String email;
	private Role role;
}
