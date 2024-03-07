package com.training.wallet.controller;

import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet/")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("save")
    public ResponseEntity<?> createWallet(@RequestBody RequestWalletDto walletDto) {
        WalletCreateDto result = walletService.createWallet(walletDto);
        return new ResponseEntity<>(result.isResult(), result.getHttpStatus());
    }

    @GetMapping(value = "/{userEmail}")
    public ResponseEntity<BalanceDto> getBalanceByUserEmail(@PathVariable String userEmail) {
        BalanceDto balanceDto = walletService.getBalanceByUserEmail(userEmail);
        return new ResponseEntity<>(balanceDto, balanceDto.getHttpStatus());
    }
}