package com.training.wallet.controller;

import com.training.wallet.dto.request.WalletDto;
import com.training.wallet.dto.response.BalanceDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Boolean> createWallet(@RequestBody WalletDto walletDto) {
        WalletCreateDto result = walletService.createWallet(walletDto);
        return new ResponseEntity<>(result.isResult(), result.getHttpStatus());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<BalanceDto> getBalanceByUserId(@PathVariable Integer userId) {
        BalanceDto balanceDto = walletService.getBalanceByUserId(userId);
        return new ResponseEntity<>(balanceDto, HttpStatus.FOUND);
    }
}