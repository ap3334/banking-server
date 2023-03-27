package com.example.bankingserver.core.transaction.repository;

import com.example.bankingserver.core.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
