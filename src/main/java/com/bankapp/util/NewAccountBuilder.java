package com.bankapp.util;

import org.springframework.stereotype.Component;

import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class NewAccountBuilder {
	private AccountBuilder accountBuilder;
	public Account toAccount(CreateAccountRequestDto bankAccountUser) {
		Account account = accountBuilder.toAccount(bankAccountUser);
		account.setAadharNumber(AccountNumberGenerator.generate13DigitAccountNumber());
		return account;
	}
}
