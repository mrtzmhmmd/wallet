package com.training.wallet.service;

import com.training.wallet.domain.model.User;
import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.ResponseWalletCreateDto;
import com.training.wallet.repository.UserRepository;
import com.training.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@SpringBootTest
public class WalletServiceImplTest {
    @Autowired
    private WalletService walletService;
    @MockBean
    private WalletRepository walletRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateWallet_Success() {
        User user = new User();
        user.setEmail("morteza@mail.com");
        RequestWalletDto walletDto = new RequestWalletDto("morteza@mail.com");
        Mockito.when(userRepository.findByEmail("morteza@mail.com")).thenReturn(Optional.of(user));
        Mockito.when(walletRepository.findByUser(user)).thenReturn(Optional.empty());
        ResponseWalletCreateDto result = walletService.createWallet(walletDto);
        Assertions.assertEquals(HttpStatus.CREATED, result.getHttpStatus());
        Assertions.assertTrue(result.isResult());
    }

    @Test
    public void testCreateWallet_UserNotFound() {
        RequestWalletDto requestWalletDto = new RequestWalletDto("morteza@mail.com");
        Mockito.when(userRepository.findByEmail("morteza@mail.com")).thenReturn(Optional.empty());
        ResponseWalletCreateDto response = walletService.createWallet(requestWalletDto);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
        Assertions.assertFalse(response.isResult());
    }
}