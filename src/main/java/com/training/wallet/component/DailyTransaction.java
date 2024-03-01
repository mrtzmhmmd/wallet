package com.training.wallet.component;

import ch.qos.logback.classic.Logger;
import com.training.wallet.domain.model.Transaction;
import com.training.wallet.repository.TransactionRepository;
import com.training.wallet.service.impl.WalletServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DailyTransaction {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WalletServiceImpl.class);

    private final TransactionRepository transactionRepository;

    @Autowired
    public DailyTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateTotalTransactions() {
        List<Transaction> transactionList = transactionRepository.findByCreatedAtToday();

        BigDecimal totalBalanceOfDay = transactionList
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        LOGGER.info("Daily amount is: " + totalBalanceOfDay);
    }
}
