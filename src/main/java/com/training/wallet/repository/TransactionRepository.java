package com.training.wallet.repository;

import com.training.wallet.domain.enums.TransactionType;
import com.training.wallet.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByTypeAndCreatedAt(TransactionType type, LocalDateTime createdAt);
}