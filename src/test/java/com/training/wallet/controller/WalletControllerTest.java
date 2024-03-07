package com.training.wallet.controller;

import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.WalletCreateDto;
import com.training.wallet.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;
    @Mock
    private WalletService walletService;

    private RequestWalletDto requestWalletDto;
    private WalletCreateDto createWalletSuccess;

    @BeforeEach
    public void  setUp() {
        requestWalletDto = new RequestWalletDto("morteza@mail.com");
        createWalletSuccess = new WalletCreateDto(true, HttpStatus.CREATED);
    }

    @Test
    public void testCreateWallet() {
        when(walletService.createWallet(requestWalletDto)).thenReturn(createWalletSuccess);
        ResponseEntity<?> response = walletController.createWallet(requestWalletDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
