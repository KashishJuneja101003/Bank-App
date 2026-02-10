package com.bankapp.service;

import com.bankapp.dto.LoginRequestDto;
import com.bankapp.dto.LoginResponseDto;

public interface AuthService {
	LoginResponseDto login(LoginRequestDto dto);
}
