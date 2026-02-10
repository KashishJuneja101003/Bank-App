package com.bankapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bankapp.dto.TransactionResponseDto;
import com.bankapp.entity.Account;
import com.bankapp.entity.Transaction;
import com.bankapp.enums.TransactionApprovalStatus;
import com.bankapp.enums.TransactionType;
import com.bankapp.exception.AccountNotFoundException;
import com.bankapp.exception.InsufficientBalanceException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.util.SecurityUtil;
import com.bankapp.util.TransactionIdGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	AccountRepository accountRepository;
	AccountServiceImpl accountServiceImpl;
	TransactionRepository transactionRepository;
	SecurityUtil securityUtil;

	@Override
	public String depositBalance(String accountNumber, BigDecimal amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);

		if (account == null) {
			throw new AccountNotFoundException("User with account number: " + accountNumber + " does not exists.");
		}

		account.setAccountBalance(account.getAccountBalance().add(amount));

		Transaction tx = new Transaction();
		tx.setTransactionId(TransactionIdGenerator.generate());
		tx.setAccount(account);
		tx.setTransactionType(TransactionType.DEPOSIT);
		tx.setAmount(amount);
		tx.setTimestamp(LocalDateTime.now());
		tx.setTransactionApprovalStatus(TransactionApprovalStatus.APPROVED);
		tx.setPerformedById(securityUtil.getLoggedInUserId());

		accountRepository.save(account);
		transactionRepository.save(tx);

		return amount + " deposited successfully in account: " + accountNumber;
	}

	@Override
	public String withdrawBalance(String accountNumber, BigDecimal amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);

		if (account == null) {
			throw new AccountNotFoundException("User with account number: " + accountNumber + " does not exists.");
		}

		BigDecimal balance = account.getAccountBalance();
		if (balance.compareTo(amount) < 0) {
			throw new InsufficientBalanceException("You have insufficient balance.");
		}

		account.setAccountBalance(balance.subtract(amount));

		Transaction tx = new Transaction();
		tx.setTransactionId(TransactionIdGenerator.generate());
		tx.setAccount(account);
		tx.setTransactionType(TransactionType.WITHDRAW);
		tx.setAmount(amount);
		tx.setTimestamp(LocalDateTime.now());
		tx.setPerformedById(securityUtil.getLoggedInUserId());

		if (amount.compareTo(BigDecimal.valueOf(200_000)) >= 0) {
			tx.setTransactionApprovalStatus(TransactionApprovalStatus.PENDING);
			tx.setApprovedBy(null); // pending manager approval
			transactionRepository.save(tx);

			return "Transaction of " + amount + " requires manager approval.";
		} else {
			// Auto-approved for amounts < 2L
			tx.setTransactionApprovalStatus(TransactionApprovalStatus.APPROVED);
			tx.setApprovedBy(null); // no manager
			account.setAccountBalance(balance.subtract(amount));

			accountRepository.save(account);
			transactionRepository.save(tx);

			return amount + " withdrew successfully from account: " + accountNumber;
		}
	}

	@Override
	public String transferBalance(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
		Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);

		if (fromAccount == null) {
			throw new AccountNotFoundException("User with account number: " + fromAccountNumber + " does not exists.");
		}

		BigDecimal balance = fromAccount.getAccountBalance();
		if (balance.compareTo(amount) < 0) {
			throw new InsufficientBalanceException("You have insufficient balance.");
		}

		Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

		if (toAccount == null) {
			throw new AccountNotFoundException("User with account number: " + toAccountNumber + " does not exists.");
		}

		fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(amount));
		toAccount.setAccountBalance(toAccount.getAccountBalance().add(amount));

		Transaction tx1 = new Transaction();
		tx1.setTransactionId(TransactionIdGenerator.generate());
		tx1.setAccount(fromAccount);
		tx1.setTransactionType(TransactionType.WITHDRAW);
		tx1.setAmount(amount);
		tx1.setTimestamp(LocalDateTime.now());
		tx1.setTransactionApprovalStatus(TransactionApprovalStatus.APPROVED);
		tx1.setPerformedById(securityUtil.getLoggedInUserId());

		accountRepository.save(fromAccount);
		transactionRepository.save(tx1);

		Transaction tx2 = new Transaction();
		tx2.setTransactionId(TransactionIdGenerator.generate());
		tx2.setAccount(toAccount);
		tx2.setTransactionType(TransactionType.DEPOSIT);
		tx2.setAmount(amount);
		tx2.setTimestamp(LocalDateTime.now());
		tx2.setTransactionApprovalStatus(TransactionApprovalStatus.APPROVED);
		tx2.setPerformedById(securityUtil.getLoggedInUserId());

		accountRepository.save(toAccount);
		transactionRepository.save(tx2);

		return amount + " is successfully from account: " + fromAccountNumber + " to account: " + toAccountNumber;
	}

	@Override
	public List<TransactionResponseDto> getTransactionsByAccount(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException("Account not found: " + accountNumber);
		}

		List<Transaction> transactions = transactionRepository.findByAccount(account);

		List<TransactionResponseDto> transactionDtos = transactions.stream()
				.map(tx -> TransactionResponseDto.builder().transactionId(tx.getTransactionId())
						.accountNumber(tx.getAccount().getAccountNumber()).transactionType(tx.getTransactionType())
						.amount(tx.getAmount()).timestamp(tx.getTimestamp())
						.transactionApprovalStatus(tx.getTransactionApprovalStatus())
						.performedById(tx.getPerformedById()).approvedBy(tx.getApprovedBy()).build())
				.toList();

		return transactionDtos;
	}

	@Override
	public TransactionResponseDto getByTransactionId(String txId) {
		Optional<Transaction> tx = transactionRepository.findById(txId);

		TransactionResponseDto txResponseDto = TransactionResponseDto.builder().transactionId(txId)
				.accountNumber(tx.get().getAccount().getAccountNumber()).transactionType(tx.get().getTransactionType())
				.amount(tx.get().getAmount()).timestamp(tx.get().getTimestamp())
				.performedById(tx.get().getPerformedById())
				.transactionApprovalStatus(tx.get().getTransactionApprovalStatus()).approvedBy(tx.get().getApprovedBy())
				.build();

		return txResponseDto;
	}
}
