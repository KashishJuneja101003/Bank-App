package com.bankapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bankapp.entity.Transaction;
import com.bankapp.enums.TransactionApprovalStatus;
import com.bankapp.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerService {
	private final TransactionRepository transactionRepository;
	
	public List<Transaction> getPendingTransactions(){
		return transactionRepository.findByTransactionApprovalStatus(TransactionApprovalStatus.PENDING);
	}
}
