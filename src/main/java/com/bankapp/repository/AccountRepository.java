package com.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankapp.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("SELECT b FROM Account b WHERE b.accountNumber = :accountNumber")
	Account findByAccountNumber(@Param("accountNumber") String accountNumber);
	
}
