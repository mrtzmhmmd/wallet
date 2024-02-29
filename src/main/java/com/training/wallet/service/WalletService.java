package com.training.wallet.service;

import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;

public interface WalletService {
    WalletCreateDto createWallet(RequestWalletDto walletDto);
    BalanceDto getBalanceByUserId(Integer userId);
}