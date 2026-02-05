package com.bankapp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.bankapp.dto.AccountResponseDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;

@Component
public class AccountBuilder {

    public Account toAccount(CreateAccountRequestDto dto) {

        String accountNumber =
                (dto.getAccountNumber() != null && !dto.getAccountNumber().isBlank())
                        ? dto.getAccountNumber()
                        : AccountNumberGenerator.generate13DigitAccountNumber();

        return Account.builder()
                .accountNumber(accountNumber)
                .accountOwnerName(dto.getOwnerName())
                .accountBalance(dto.getBalance())
                .accountStatus(dto.getAccountStatus())
                .accountType(dto.getAccountType())
                .creationDate(LocalDate.now())
                .lastUpdationDate(LocalDateTime.now())
                .dateOfBirth(dto.getDateOfBirth())
                .ownerGender(dto.getOwnerGender())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .panNumber(dto.getPanNumber())
                .aadharNumber(dto.getAadharNumber())
                .address(dto.getAddress())
                .build();
    }
    
    public Account updateAccount(AccountResponseDto dto) {
        Account account = Account.builder()
        		.accountNumber(dto.getAccountNumber())
                .accountOwnerName(dto.getOwnerName())
                .accountBalance(dto.getBalance())
                .accountStatus(dto.getAccountStatus())
                .accountType(dto.getAccountType())
                .creationDate(LocalDate.now())
                .lastUpdationDate(LocalDateTime.now())
                .dateOfBirth(dto.getDateOfBirth())
                .ownerGender(dto.getOwnerGender())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .panNumber(dto.getPanNumber())
                .aadharNumber(dto.getAadharNumber())
                .address(dto.getAddress())
                .build();
        		
        return account;
    }
}
