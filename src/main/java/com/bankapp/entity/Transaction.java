package com.bankapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bankapp.enums.TransactionApprovalStatus;
import com.bankapp.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")

public class Transaction {
	@Id
	@Column(nullable = false)
	private String transactionId;
	
	@ManyToOne
	@JoinColumn(name = "account_number", nullable = false)
	private Account account;
	
	@Column(nullable = false)
	private TransactionType transactionType;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private LocalDateTime timestamp;
	
//	@Column(nullable = false)
	private String performedById;	// Clerk ID
	
	@Column(nullable = false)
	private TransactionApprovalStatus transactionApprovalStatus;
	
	private String approvedBy;	// Manager ID, optional
}
