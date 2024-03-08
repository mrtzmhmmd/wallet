package com.training.wallet.service;

import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.ResponseWalletCreateDto;

public interface WalletService {
    ResponseWalletCreateDto createWallet(RequestWalletDto walletDto);
    BalanceDto getBalanceByUserEmail(String userEmail);
}