package com.training.wallet.service.impl;

import com.training.wallet.dto.request.WalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.model.Wallet;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletCreateDto createWallet(WalletDto walletDto) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(walletDto.getUserId());
        if(optionalWallet.isPresent()) {
            return WalletCreateDto.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .result(false)
                    .build();
        }
        Wallet wallet = Wallet.builder()
                .userId(walletDto.getUserId())
                .build();
        walletRepository.save(wallet);
        return WalletCreateDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .result(true)
                .build();

    }

    @Override
    public BalanceDto getBalanceByUserId(String userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new NoSuchElementException("Not found wallet with this user id: " +
                                userId));

        BigDecimal balance = wallet.getBalance();
        return BalanceDto.builder()
                .balance(balance)
                .build();
    }
}