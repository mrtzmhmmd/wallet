package com.training.wallet.service.impl;

import com.training.wallet.dto.request.TransactionDto;
import com.training.wallet.dto.response.TransactionResponseDto;
import com.training.wallet.model.Transaction;
import com.training.wallet.model.Wallet;
import com.training.wallet.repository.TransactionRepository;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDto addBalance(TransactionDto transactionDto) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(transactionDto.getUserId());
        if (optionalWallet.isEmpty()) {
            return TransactionResponseDto.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Not found wallet with user id: " + transactionDto.getUserId())
                    .build();
        }
        Wallet wallet = optionalWallet.get();
        int balance = wallet.getBalance();
        int amount = transactionDto.getAmount();
        int newBalance = balance + amount;
        Transaction transaction;
        if (newBalance >= 0) {
            String transactionId = updateWalletAndSaveTransaction(wallet, newBalance, amount);
            return buildResponse(HttpStatus.OK, transactionId);
        }
        transaction = Transaction.builder()
                .wallet(wallet)
                .amount(amount)
                .status(false)
                .build();
        transactionRepository.save(transaction);
        return buildResponse(HttpStatus.BAD_REQUEST, "Wallet doesn't have this amount");
    }

    private String updateWalletAndSaveTransaction(Wallet wallet, Integer newBalance, Integer amount) {
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .wallet(wallet)
                .status(true)
                .build();
        transaction = transactionRepository.save(transaction);
        return transaction.getTransactionId();
    }

    private TransactionResponseDto buildResponse(HttpStatus status, String message) {
        return TransactionResponseDto.builder()
                .httpStatus(status)
                .message(message)
                .build();
    }
}
