package com.bankapp.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class CreateAccountRequestDto {
	private String ownerName;
	private BigDecimal balance;
}
