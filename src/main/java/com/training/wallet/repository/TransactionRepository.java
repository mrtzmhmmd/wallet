package com.training.wallet.repository;

import com.training.wallet.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE FUNCTION('DATE', t.createdAt) = CURRENT_DATE")
    List<Transaction> findByCreatedAtToday();
}