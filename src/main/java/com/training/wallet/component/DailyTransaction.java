package com.training.wallet.component;

import com.training.wallet.model.Transaction;
import com.training.wallet.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class DailyTransaction {

    private final TransactionRepository transactionRepository;

    @Autowired
    public DailyTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateTotalTransactions() {
        LocalDate now = LocalDate.now();
        List<Transaction> transactionList = transactionRepository.findByCreatedAtAndStatus(now, true);
        int totalAmount = transactionList.stream()
                .mapToInt(Transaction::getAmount)
                .sum();
        log.info("Daily amount is: " + totalAmount);
    }
}
