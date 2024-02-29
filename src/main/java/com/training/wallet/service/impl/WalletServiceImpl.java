package com.training.wallet.service.impl;

import com.training.wallet.domain.model.Wallet;
import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletCreateDto createWallet(RequestWalletDto walletDto) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(walletDto.getUserId());
        if(optionalWallet.isPresent()) {
            return WalletCreateDto.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .result(false)
                    .build();
        }
        Wallet wallet = Wallet.builder()
                .userId(walletDto.getUserId())
                .balance(BigDecimal.ZERO)
                .build();
        walletRepository.save(wallet);
        return WalletCreateDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .result(true)
                .build();

    }

    @Override
    public BalanceDto getBalanceByUserId(Integer userId) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
        if(optionalWallet.isPresent()) {
            return BalanceDto.builder()
                    .balance(optionalWallet.get().getBalance())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return BalanceDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}