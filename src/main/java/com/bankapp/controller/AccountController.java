package com.bankapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;
import com.bankapp.service.AccountService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
	
	private AccountService bankAppAccountService;
	
	@GetMapping("/{accountNumber}")
	public AccountResponseDto getUserByAccountNumber(@PathVariable String accountNumber) {
		return bankAppAccountService.getUserByAccountNumber(accountNumber);
	}
	
	@PostMapping
	public AccountResponseDto addUser(@RequestBody CreateAccountRequestDto newUser) {
		return bankAppAccountService.addUser(newUser);
	}
	
	@GetMapping("/users")
	public UserListResponseDto getAllUsers(){
		return bankAppAccountService.getAllUsers();
	}
}
