package com.bankapp.dto;

import com.bankapp.entity.Address;

import lombok.Getter;

@Getter
public class UpdateAccountRequestDto {
	private String ownerName;
	
	private String phoneNumber;
	
	private String email;
	
	private Address address;

}
