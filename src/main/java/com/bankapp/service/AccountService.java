package com.bankapp.service;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UpdateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;
import com.bankapp.enums.AccountStatus;

public interface AccountService {
	public AccountResponseDto getUserByAccountNumber(String accountNumber);
	public AccountResponseDto getUserByEmail(String email);
	public AccountResponseDto getUserByPanNumber(String panNo);
	public AccountResponseDto getUserByAadharNumber(String aadharNum);
	public UserListResponseDto getAllUsers();
	public UserListResponseDto getAllUsersByAccountStatus(AccountStatus accountStatus);
	public AccountResponseDto addUser(CreateAccountRequestDto newUser);
	public String deleteUserByAccountNumber(String accountNumber);
	public String updateUserByAccountNumber(String accountNumber,  UpdateAccountRequestDto dto);
	
}
