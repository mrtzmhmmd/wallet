package com.training.wallet.service;

import com.training.wallet.dto.request.WalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;

public interface WalletService {
    WalletCreateDto createWallet(WalletDto walletDto);
    BalanceDto getBalanceByUserId(Integer userId);
}