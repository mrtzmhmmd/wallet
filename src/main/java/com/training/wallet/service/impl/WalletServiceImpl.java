package com.training.wallet.service.impl;

import ch.qos.logback.classic.Logger;
import com.training.wallet.domain.model.Wallet;
import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.WalletService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletCreateDto createWallet(RequestWalletDto walletDto) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(walletDto.getUserId());
        if(optionalWallet.isPresent()) {
            LOGGER.error("user id: '{}' have wallet", walletDto.getUserId());
            return WalletCreateDto.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .result(false)
                    .build();
        }
        Wallet wallet = Wallet.builder()
                .userId(walletDto.getUserId())
                .balance(BigDecimal.ZERO)
                .build();
        Wallet saveWallet = walletRepository.save(wallet);
        LOGGER.info("Wallet created successfully: '{}'", saveWallet.getId());
        return WalletCreateDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .result(true)
                .build();

    }

    @Override
    public BalanceDto getBalanceByUserId(Integer userId) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
        if(optionalWallet.isPresent()) {
            LOGGER.info("user id: '{}', balance: '{}'", userId, optionalWallet.get().getBalance());
            return BalanceDto.builder()
                    .balance(optionalWallet.get().getBalance())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        LOGGER.error("user id: '{}' doesn't have wallet", userId);
        return BalanceDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}