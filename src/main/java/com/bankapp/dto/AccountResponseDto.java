package com.bankapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bankapp.entity.Address;
import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.AccountType;
import com.bankapp.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
	private String accountNumber;
	private String ownerName;
	private BigDecimal balance;
	private String ifscCode;
	private AccountStatus accountStatus;
	private AccountType accountType;
	private LocalDate creationDate;
	private LocalDateTime lastUpdationDate;
	private Gender ownerGender;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private String email;
	private String panNumber;
	private String aadharNumber;
	private Address address;

}
