package com.bankapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;
import com.bankapp.entity.Account;
import com.bankapp.exception.AccountNotFoundException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.util.AccountNumberGenerator;

@Service
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	@Value("${bank.ifsc}")
	private String bankIfsc;

	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountResponseDto getUserByAccountNumber(String accountNumber) {
		Account bankAccount = accountRepository.findByAccountNumber(accountNumber);
		if (bankAccount == null) {
			throw new AccountNotFoundException(
					"Bank Account for account number: '" + accountNumber + "' is not found.");
		}

		return new AccountResponseDto(bankAccount.getAccountNumber(), bankAccount.getAccountOwnerName(),
				bankAccount.getAccountBalance(), bankIfsc);
	}

	@Override
	public UserListResponseDto getAllUsers() {
		List<AccountResponseDto> allUsers = accountRepository.findAll().stream()
				.map(a -> AccountResponseDto.builder().accountNumber(a.getAccountNumber())
						.balance(a.getAccountBalance()).ownerName(a.getAccountOwnerName()).build())
				.collect(Collectors.toList());
		
		if (allUsers.size() == 0) {
			throw new AccountNotFoundException("There are no users in your bank");
		}
		return new UserListResponseDto(allUsers);
	}

	@Override
	public AccountResponseDto addUser(CreateAccountRequestDto bankAccountUser) {
//		boolean userExists = accountRepository.findById(bankAccountUser.getId()).isPresent();
//		
//		if(userExists) {
//			throw new AccountAlreadyExistsException("Account already exists with account number: " 
//		            + bankAccountUser.getAccountNumber());
//		}

		Account account = new Account();
		account.setAccountNumber(AccountNumberGenerator.generate13DigitAccountNumber());
		account.setAccountOwnerName(bankAccountUser.getOwnerName());
		account.setAccountBalance(bankAccountUser.getBalance());

		accountRepository.save(account);

		AccountResponseDto accountResponseDto = AccountResponseDto.builder().accountNumber(account.getAccountNumber())
				.balance(account.getAccountBalance()).ownerName(account.getAccountOwnerName()).ifscCode(bankIfsc)
				.build();

		return accountResponseDto;
	}

}
