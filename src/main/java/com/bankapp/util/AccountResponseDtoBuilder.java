package com.bankapp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.entity.Account;

@Component
public class AccountResponseDtoBuilder {
	
	@Value("${bank.ifsc}")
	private String bankIfsc;
	
	public AccountResponseDto toDto(Account bankAccount) {
		return AccountResponseDto.builder()
				.accountNumber(bankAccount.getAccountNumber())
				.ownerName(bankAccount.getAccountOwnerName())
				.balance(bankAccount.getAccountBalance())
				.ifscCode(bankIfsc)
				.accountStatus(bankAccount.getAccountStatus())
				.accountType(bankAccount.getAccountType())
				.creationDate(bankAccount.getCreationDate())
				.lastUpdationDate(bankAccount.getLastUpdationDate())
				.ownerGender(bankAccount.getOwnerGender())
				.dateOfBirth(bankAccount.getDateOfBirth())
				.phoneNumber(bankAccount.getPhoneNumber())
				.email(bankAccount.getEmail())
				.panNumber(bankAccount.getPanNumber())
				.aadharNumber(bankAccount.getAadharNumber())
				.address(bankAccount.getAddress())
				.build();
	}
}
