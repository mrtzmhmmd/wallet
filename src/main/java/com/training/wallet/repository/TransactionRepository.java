package com.training.wallet.repository;

import com.training.wallet.model.Transaction;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Registered
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByCreatedAtAndStatus(LocalDate createDate, boolean status);
}