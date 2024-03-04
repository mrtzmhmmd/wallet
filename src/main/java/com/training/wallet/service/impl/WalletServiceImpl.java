package com.training.wallet.service.impl;

import ch.qos.logback.classic.Logger;
import com.training.wallet.domain.model.User;
import com.training.wallet.domain.model.Wallet;
import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.repository.UserRepository;
import com.training.wallet.repository.WalletRepository;
import com.training.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public WalletCreateDto createWallet(RequestWalletDto walletDto) {
        Optional<User> optionalUser = userRepository.findByEmail(walletDto.getEmail());
        if(optionalUser.isEmpty()) {
            LOGGER.error("user with email: '{}' does not exist", walletDto.getEmail());
            return WalletCreateDto.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .result(false)
                    .build();
        }
        Optional<Wallet> optionalWallet = walletRepository.findByUser(optionalUser.get());
        if(optionalWallet.isPresent()) {
            LOGGER.error("user with email: '{}' have wallet", walletDto.getEmail());
            return WalletCreateDto.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .result(false)
                    .build();
        }

        Wallet wallet = Wallet.builder()
                .user(optionalUser.get())
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
    public BalanceDto getBalanceByUserEmail(String userEmail) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserEmail(userEmail);
        if(optionalWallet.isPresent()) {
            LOGGER.info("user with email: '{}', balance: '{}'", userEmail, optionalWallet.get().getBalance());
            return BalanceDto.builder()
                    .balance(optionalWallet.get().getBalance())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        LOGGER.error("user with email: '{}' doesn't have wallet", userEmail);
        return BalanceDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}