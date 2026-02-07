package com.bankapp.service;

import java.math.BigDecimal;
import java.util.List;

import com.bankapp.dto.TransactionResponseDto;

public interface TransactionService {
	public String depositBalance(String accountNumber, BigDecimal amount);
	public String withdrawBalance(String accountNumber, BigDecimal amount);
	public String transferBalance(String fromAccountNumber, String toAccountNumber, BigDecimal amount);
	public List<TransactionResponseDto> getTransactionsByAccount(String account);
	public TransactionResponseDto getByTransactionId(String txId);
}