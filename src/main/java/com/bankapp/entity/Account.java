package com.bankapp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.AccountType;
import com.bankapp.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "bank_accounts", uniqueConstraints = {
		@UniqueConstraint(name = "uk_account_number", columnNames = "accountNumber"),
		@UniqueConstraint(name = "uk_email", columnNames = "email"),
		@UniqueConstraint(name = "uk_pan", columnNames = "panNumber"),
		@UniqueConstraint(name = "uk_aadhar", columnNames = "aadharNumber") })


public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 13)
	private String accountNumber;

	@Column(nullable = false)
	private String accountOwnerName;

	@Column(nullable = false)
	private BigDecimal accountBalance;

	@Transient
	private String ifscCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_status", nullable = false)
	private AccountStatus accountStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;

	@Column(nullable = false)
	private LocalDate creationDate;

	@Column(nullable = false)
	private LocalDateTime lastUpdationDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender ownerGender;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String panNumber;

	@Column(nullable = false, unique = true, length=12)
	private String aadharNumber;

	@Embedded
	private Address address;

	@Column(nullable = false)

	@OneToMany(mappedBy = "account")
	@JsonManagedReference
	private List<Transaction> transactions;
}