package com.training.wallet.service;

import com.training.wallet.dto.request.TransactionDto;
import com.training.wallet.dto.response.TransactionResponseDto;

public interface TransactionService {
    TransactionResponseDto addBalance(TransactionDto transactionDto);
}
