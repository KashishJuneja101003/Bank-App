package com.bankapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bankapp.enums.TransactionApprovalStatus;
import com.bankapp.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto {
	private String transactionId;
	private String accountNumber;
	private TransactionType transactionType;
	private BigDecimal amount;
	private LocalDateTime timestamp;
	private Integer performedById;	// Clerk ID
	private TransactionApprovalStatus transactionApprovalStatus;
	private Integer approvedBy;	// Manager ID, optional
}
