package com.bankapp.dto;

import com.bankapp.entity.Address;
import com.bankapp.enums.AccountStatus;

import lombok.Getter;

@Getter
public class UpdateAccountRequestDto {
	private String ownerName;
	
	private String phoneNumber;
	
	private String email;
	
	private Address address;

	private AccountStatus accountStatus;
}
