package com.bankapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.bankapp.enums.TransactionApprovalStatus;

public class Transaction {
	private Integer transactionId;
	private Account bankAccount;
	private BigDecimal amount;
	private Date date;
	private Integer performedById;	// Clerk ID
	private TransactionApprovalStatus transactionApprovalStatus;
	private Integer approvedBy; // Manager ID, if applicable
}
