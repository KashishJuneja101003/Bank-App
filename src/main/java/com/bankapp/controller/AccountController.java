package com.bankapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UpdateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;
import com.bankapp.service.AccountService;

import jakarta.validation.Valid;
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
	public AccountResponseDto addUser(@Valid @RequestBody CreateAccountRequestDto newUser) {
		return bankAppAccountService.addUser(newUser);
	}

	@GetMapping("/users")
	public UserListResponseDto getAllUsers() {
		return bankAppAccountService.getAllUsers();
	}

	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteUserByAccountNumber(@PathVariable String accountNumber) {
		String message = bankAppAccountService.deleteUserByAccountNumber(accountNumber);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(message);
	}

	@PatchMapping("/update/{accountNumber}")
	public ResponseEntity<String> updateUserByAccountNumber(@PathVariable String accountNumber, 
			@RequestBody UpdateAccountRequestDto updateAccountRequestDto) {
		
		String message = bankAppAccountService.updateUserByAccountNumber(accountNumber, updateAccountRequestDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(message);
	}
}
