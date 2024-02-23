package com.training.wallet.controller;

import com.training.wallet.dto.request.TransactionDto;
import com.training.wallet.dto.response.TransactionResponseDto;
import com.training.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet/")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/add-amount")
    public ResponseEntity<String> addAmount(@RequestBody TransactionDto transactionDto) {
        TransactionResponseDto transactionResponseDto = transactionService.addBalance(transactionDto);
        return new ResponseEntity<>(transactionResponseDto.getMessage(), transactionResponseDto.getHttpStatus());
    }

}
