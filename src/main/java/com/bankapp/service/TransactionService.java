package com.bankapp.service;

import java.math.BigDecimal;

import com.bankapp.dto.AccountResponseDto;

public interface TransactionService {
	public void depositBalance(AccountResponseDto bankAccountUser, BigDecimal amount);
	public void withdrawBalance(AccountResponseDto bankAccountUser, BigDecimal amount);
}
