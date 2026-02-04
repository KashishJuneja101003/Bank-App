package com.bankapp.service;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;

public interface AccountService {
	public AccountResponseDto getUserByAccountNumber(String accountNumber);
	public UserListResponseDto getAllUsers();
	public AccountResponseDto addUser(CreateAccountRequestDto newUser);
}
