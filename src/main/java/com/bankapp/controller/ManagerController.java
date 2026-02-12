package com.bankapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.entity.Account;
import com.bankapp.entity.Transaction;
import com.bankapp.enums.Role;
import com.bankapp.enums.TransactionApprovalStatus;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.service.ManagerService;
import com.bankapp.util.SecurityUtil;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {
	private final ManagerService managerService;
	private final SecurityUtil securityUtil;
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	@GetMapping("/pending-transactions")
	public ResponseEntity<List<Transaction>> getPendingTransactions() {
		return ResponseEntity.ok(managerService.getPendingTransactions());
	}
	
	@PatchMapping("/pending-transactions/approve/{transactionId}")
	public ResponseEntity<String> approveTransaction(@PathVariable String transactionId) {
		Integer loggedInUserId = securityUtil.getLoggedInUserId();
	    Role role = securityUtil.getLoggedInUserRole();

	    if (role != Role.MANAGER) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("Only managers can approve transactions");
	    }
	    
	    Transaction txn = transactionRepository.findById(transactionId)
	            .orElseThrow(() -> new RuntimeException("Transaction not found"));
	    
	    if (txn.getTransactionApprovalStatus() != TransactionApprovalStatus.PENDING) {
	        return ResponseEntity.badRequest().body("Transaction is not pending approval");
	    }
	    
	    Account account = txn.getAccount();
	    BigDecimal balance = account.getAccountBalance();
	    if (balance.compareTo(txn.getAmount()) < 0) {
	        return ResponseEntity.badRequest().body("Insufficient balance to approve this transaction");
	    }
	    
	    account.setAccountBalance(balance.subtract(txn.getAmount()));
	    txn.setTransactionApprovalStatus(TransactionApprovalStatus.APPROVED);
	    txn.setApprovedBy(loggedInUserId);

	    accountRepository.save(account);
	    transactionRepository.save(txn);

	    return ResponseEntity.ok("Transaction approved successfully");
	}
	
	@PatchMapping("/pending-transactions/decline/{transactionId}")
	public ResponseEntity<String> declineTransaction(@PathVariable String transactionId) {
		Integer loggedInUserId = securityUtil.getLoggedInUserId();
	    Role role = securityUtil.getLoggedInUserRole();

	    if (role != Role.MANAGER) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("Only managers can approve transactions");
	    }
	    
	    Transaction txn = transactionRepository.findById(transactionId)
	            .orElseThrow(() -> new RuntimeException("Transaction not found"));
	    
	    if (txn.getTransactionApprovalStatus() != TransactionApprovalStatus.PENDING) {
	        return ResponseEntity.badRequest().body("Transaction is not pending approval");
	    }
	    
	    Account account = txn.getAccount();
	    BigDecimal balance = account.getAccountBalance();
	    if (balance.compareTo(txn.getAmount()) < 0) {
	        return ResponseEntity.badRequest().body("Insufficient balance to approve this transaction");
	    }
	    
	    txn.setTransactionApprovalStatus(TransactionApprovalStatus.DECLINED);
	    txn.setApprovedBy(loggedInUserId);

	    accountRepository.save(account);
	    transactionRepository.save(txn);

	    return ResponseEntity.ok("Transaction declined");
	}
	
}
