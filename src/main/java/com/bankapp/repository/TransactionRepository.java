package com.bankapp.repository;

import java.math.BigDecimal;

import com.bankapp.dto.AccountResponseDto;

public interface TransactionRepository {
	public void depositBalance(AccountResponseDto bankAccountUser, BigDecimal amount);
	public void withdrawBalance(AccountResponseDto bankAccountUser, BigDecimal amount);
}
