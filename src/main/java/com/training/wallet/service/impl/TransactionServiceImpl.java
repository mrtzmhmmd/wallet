package com.training.wallet.service.impl;

import ch.qos.logback.classic.Logger;
import com.training.wallet.domain.enums.TransactionType;
import com.training.wallet.domain.model.Transaction;
import com.training.wallet.domain.model.Wallet;
import com.training.wallet.dto.request.TransactionDto;
import com.training.wallet.dto.response.TransactionResponseDto;
import com.training.wallet.repository.TransactionRepository;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.TransactionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDto executeTransaction(TransactionDto transactionDto) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserEmail(transactionDto.userEmail());
        if (optionalWallet.isEmpty()) {
            LOGGER.error("user with email: '{}' doesn't have wallet", transactionDto.userEmail());
            return buildResponse(HttpStatus.NOT_FOUND,
                    "Not found wallet with user with email: " + transactionDto.userEmail());
        }
        Wallet wallet = optionalWallet.get();
        BigDecimal balance = wallet.getBalance();
        BigDecimal amount = transactionDto.amount();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            if(balance.compareTo(amount.abs()) < 0) {
                LOGGER.error("user with email: '{}' doesn't have sufficient balance", transactionDto.userEmail());
                return buildResponse(HttpStatus.BAD_REQUEST,
                        "Wallet doesn't have sufficient balance");
            } else {
                return processTransaction(wallet, balance, amount, TransactionType.WITHDRAWAL, transactionDto.userEmail());
            }
        }
        return processTransaction(wallet, balance, amount, TransactionType.DEPOSIT, transactionDto.userEmail());
    }

    private TransactionResponseDto processTransaction(Wallet wallet,
                                                      BigDecimal balance,
                                                      BigDecimal amount,
                                                      TransactionType type,
                                                      String userEmail) {
        BigDecimal newBalance = balance.add(amount);
        String transactionId = updateWalletAndSaveTransaction(wallet, newBalance, amount, type);
        LOGGER.info("Transaction committed. Type: {}, User email: '{}', Balance: {}, Amount: {}", type, userEmail, newBalance, amount);
        return buildResponse(HttpStatus.OK, transactionId);
    }
    private String updateWalletAndSaveTransaction(Wallet wallet,
                                                  BigDecimal newBalance,
                                                  BigDecimal amount,
                                                  TransactionType type) {
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .wallet(wallet)
                .type(type)
                .build();
        transaction = transactionRepository.save(transaction);
        return transaction.getId();
    }

    private TransactionResponseDto buildResponse(HttpStatus status, String message) {
        return TransactionResponseDto.builder()
                .httpStatus(status)
                .message(message)
                .build();
    }
}
