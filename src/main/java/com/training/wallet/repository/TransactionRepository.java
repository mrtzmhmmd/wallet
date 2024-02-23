package com.training.wallet.repository;

import com.training.wallet.model.Transaction;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}