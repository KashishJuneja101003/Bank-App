package com.bankapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.dto.UpdateAccountRequestDto;
import com.bankapp.dto.UserListResponseDto;
import com.bankapp.entity.Account;
import com.bankapp.entity.Address;
import com.bankapp.enums.AccountStatus;
import com.bankapp.exception.AccountNotFoundException;
import com.bankapp.exception.UpdateRequestBodyValidationException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.util.AccountBuilder;
import com.bankapp.util.AccountResponseDtoBuilder;
import com.bankapp.util.NewAccountBuilder;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountBuilder accountBuilder;
	private AccountRepository accountRepository;
	private NewAccountBuilder newAccountBuilder;

	private AccountResponseDtoBuilder builder;

	public AccountServiceImpl(AccountRepository accountRepository, NewAccountBuilder newAccountBuilder,
			AccountResponseDtoBuilder builder, AccountBuilder accountBuilder) {
		this.accountRepository = accountRepository;
		this.newAccountBuilder = newAccountBuilder;
		this.builder = builder;
		this.accountBuilder = accountBuilder;
	}

	@Override
	public AccountResponseDto getUserByAccountNumber(String accountNumber) {
		Account bankAccount = accountRepository.findByAccountNumber(accountNumber);
		if (bankAccount == null) {
			throw new AccountNotFoundException(
					"Bank Account for account number: '" + accountNumber + "' is not found.");
		}

		return builder.toDto(bankAccount);
	}
	
	@Override
	public AccountResponseDto getUserByEmail(String email) {
		Account bankAccount = accountRepository.findByEmail(email);
		if (bankAccount == null) {
			throw new AccountNotFoundException(
					"Bank Account for account number: '" + email + "' is not found.");
		}

		return builder.toDto(bankAccount);
	}

	@Override
	public AccountResponseDto getUserByPanNumber(String panNo) {
		Account bankAccount = accountRepository.findByPanNumber(panNo);
		if (bankAccount == null) {
			throw new AccountNotFoundException(
					"Bank Account for account number: '" + panNo + "' is not found.");
		}

		return builder.toDto(bankAccount);
	}

	@Override
	public AccountResponseDto getUserByAadharNumber(String aadharNum) {
		Account bankAccount = accountRepository.findByAadharNumber(aadharNum);
		if (bankAccount == null) {
			throw new AccountNotFoundException(
					"Bank Account for account number: '" + aadharNum + "' is not found.");
		}

		return builder.toDto(bankAccount);
	}

	@Override
	public UserListResponseDto getAllUsers() {
		List<AccountResponseDto> allUsers = accountRepository.findAll().stream().map(a -> builder.toDto(a))
				.collect(Collectors.toList());

		if (allUsers.isEmpty()) {
			throw new AccountNotFoundException("There are no users in your bank");
		}
		return new UserListResponseDto(allUsers);
	}

	@Override
	public UserListResponseDto getAllUsersByAccountStatus(AccountStatus accountStatus) {
		List<AccountResponseDto> allUsers = accountRepository.findByAccountStatus(accountStatus).stream().map(a -> builder.toDto(a))
				.collect(Collectors.toList());

		if (allUsers.isEmpty()) {
			throw new AccountNotFoundException("There are no users in your bank");
		}
		return new UserListResponseDto(allUsers);
	}

	
	@Override
	public AccountResponseDto addUser(CreateAccountRequestDto bankAccountUser) {

		Account account = newAccountBuilder.toAccount(bankAccountUser);
		accountRepository.save(account);

		return builder.toDto(account);
	}

	@Override
	public String deleteUserByAccountNumber(String accountNumber) {
		accountRepository.delete(getUserByAccountNumber(accountNumber).getAccountNumber(), AccountStatus.FROZEN);

		return "Account with number " + accountNumber + " deleted successfully!";
	}

	@Override
	public String updateUserByAccountNumber(String accountNumber, UpdateAccountRequestDto dto) {
		AccountResponseDto accountResponseDto = getUserByAccountNumber(accountNumber);
		Account account = accountBuilder.updateAccount(accountResponseDto);

		String ownerName = dto.getOwnerName();
		String phoneNumber = dto.getPhoneNumber();
		String email = dto.getEmail();
		Address address = dto.getAddress();
		String status = dto.getAccountStatus().name();

		if (ownerName == null && phoneNumber == null && email == null && address == null && status == null) {
			throw new UpdateRequestBodyValidationException(
					"Empty Update Request. You can update name, phone number, email and address.");
		}

		if (ownerName == null)
			ownerName = account.getAccountOwnerName();
		if (phoneNumber == null)
			phoneNumber = account.getPhoneNumber();
		if (email == null)
			email = account.getEmail();
		if (address == null)
			address = account.getAddress();
		if (status == null)
			status = account.getAccountStatus().name();

		accountRepository.updateByAccountNumber(accountNumber, ownerName, phoneNumber, email, status, LocalDateTime.now());
		

		return "Account with number " + accountNumber + " updated successfully!";
	}

	

}
