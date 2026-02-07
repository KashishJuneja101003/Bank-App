package com.bankapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.TransactionResponseDto;
import com.bankapp.service.TransactionServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {
	private TransactionServiceImpl transactionServiceImpl;

	
	@PostMapping("/deposit/{accountNumber}")
	public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestBody BigDecimal amount){
		String message = transactionServiceImpl.depositBalance(accountNumber, amount);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(message);
	}
	
	@PostMapping("/withdraw/{accountNumber}")
	public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestBody BigDecimal amount){
		String message = transactionServiceImpl.withdrawBalance(accountNumber, amount);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(message);
	}
	
	@PostMapping("/transfer/{fromAccountNumber}/{toAccountNumber}")
	public ResponseEntity<String> transfer(@PathVariable String fromAccountNumber, @PathVariable String toAccountNumber, @RequestBody BigDecimal amount){
		String message = transactionServiceImpl.transferBalance(fromAccountNumber, toAccountNumber, amount);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(message);
	}
	
	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<List<TransactionResponseDto>> getTransactionsByAccount(@PathVariable String accountNumber){
	    List<TransactionResponseDto> transactions = transactionServiceImpl.getTransactionsByAccount(accountNumber);
	    return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/id/{txId}")
	public ResponseEntity<TransactionResponseDto> getByTransactionId(@PathVariable String txId){
	    TransactionResponseDto transactions = transactionServiceImpl.getByTransactionId(txId);
	    return ResponseEntity.ok(transactions);
	}

}
