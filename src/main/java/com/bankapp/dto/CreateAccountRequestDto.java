package com.bankapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bankapp.entity.Address;
import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.AccountType;
import com.bankapp.enums.Gender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class CreateAccountRequestDto {

    @NotBlank(message = "Owner name is mandatory")
    private String ownerName;

    private String accountNumber = "";
    
    @NotNull(message = "Balance is mandatory")
    @PositiveOrZero(message = "Balance cannot be negative")
    private BigDecimal balance;

    @NotNull(message = "Account status is mandatory")
    private AccountStatus accountStatus;

    @NotNull(message = "Account type is mandatory")
    private AccountType accountType;

    @NotNull(message = "Gender is mandatory")
    private Gender ownerGender;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "PAN number is mandatory")
    private String panNumber;

    @NotBlank(message = "Aadhar number is mandatory")
    private String aadharNumber;

    @NotNull(message = "Address is mandatory")
    @Valid
    private Address address;
}
