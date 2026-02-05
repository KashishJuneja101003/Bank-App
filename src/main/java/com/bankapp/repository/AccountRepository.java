package com.bankapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankapp.entity.Account;

import jakarta.transaction.Transactional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
	Account findByAccountNumber(@Param("accountNumber") String accountNumber);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Account a WHERE a.accountNumber = :accountNumber")
	void delete(@Param("accountNumber") String accountNumber);	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Bank_Accounts SET account_owner_name = :ownerName, phone_number = :phoneNumber, email = :email, last_updation_date = :lastUpdationDate WHERE account_number = :accountNumber", nativeQuery = true)
	int updateByAccountNumber(@Param("accountNumber") String accountNumber,
	                        @Param("ownerName") String ownerName,
	                        @Param("phoneNumber") String phoneNumber,
	                        @Param("email") String email,
	                        @Param("lastUpdationDate") LocalDateTime lastUpdationDate
			);

}
