package com.training.wallet.controller;

import com.training.wallet.config.JwtAuthenticationFilter;
import com.training.wallet.config.SecurityTestConfig;
import com.training.wallet.dto.request.RequestWalletDto;
import com.training.wallet.dto.response.ResponseWalletCreateDto;
import com.training.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
@Import(SecurityTestConfig.class)
public class WalletControllerTest {
    @MockBean
    WalletService walletService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    MockMvc mockMvc;

    private RequestWalletDto requestWalletDto;
    private ResponseWalletCreateDto createWalletSuccess;

    @BeforeEach
    public void  setUp() {
        requestWalletDto = new RequestWalletDto("morteza@mail.com");
        createWalletSuccess = new ResponseWalletCreateDto(true, HttpStatus.CREATED);
    }

    @Test
    public void testCreateWallet_Success() throws Exception {
        Mockito.when(walletService.createWallet(requestWalletDto)).thenReturn(createWalletSuccess);
        mockMvc.perform(post("/api/v1/wallet/save")).andExpect(status().isOk());
    }
}
