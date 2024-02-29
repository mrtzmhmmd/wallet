package com.training.wallet.service.impl;

import com.training.wallet.domain.enums.TransactionType;
import com.training.wallet.dto.request.TransactionDto;
import com.training.wallet.dto.response.TransactionResponseDto;
import com.training.wallet.domain.model.Transaction;
import com.training.wallet.domain.model.Wallet;
import com.training.wallet.repository.TransactionRepository;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            return buildResponse(HttpStatus.NOT_FOUND,
                    "Not found wallet with user id: " + transactionDto.getUserId());
        }
        Wallet wallet = optionalWallet.get();
        BigDecimal balance = wallet.getBalance();
        BigDecimal amount = transactionDto.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            if(balance.compareTo(amount.abs()) < 0) {
                return buildResponse(HttpStatus.BAD_REQUEST,
                        "Wallet doesn't have sufficient amount");
            } else {
                BigDecimal newBalance = balance.add(amount);
                String transactionId = updateWalletAndSaveTransaction(wallet, newBalance, amount, TransactionType.WITHDRAWAL);
                return buildResponse(HttpStatus.OK, transactionId);
            }
        }
        BigDecimal newBalance = balance.add(amount);
        String transactionId = updateWalletAndSaveTransaction(wallet,
                newBalance,
                amount,
                TransactionType.DEPOSIT);
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
