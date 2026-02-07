package com.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankapp.entity.Account;
import com.bankapp.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	
	public List<Transaction> findByAccount(Account account);
}