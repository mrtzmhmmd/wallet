package com.training.wallet.controller;

import com.training.wallet.config.JwtAuthenticationFilter;
import com.training.wallet.config.SecurityTestConfig;
import com.training.wallet.dto.request.AuthenticationRequest;
import com.training.wallet.dto.request.RegisterRequest;
import com.training.wallet.dto.response.AuthenticationResponse;
import com.training.wallet.service.AuthenticationService;
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

@WebMvcTest(AuthenticationController.class)
@Import(SecurityTestConfig.class)
public class AuthenticationControllerTest {

    @MockBean
    AuthenticationService authenticationService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    MockMvc mockMvc;

    private RegisterRequest validRequest;
    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse successResponse;

    @BeforeEach
    public void setUp() {
        validRequest = new RegisterRequest("Morteza",
                "Mohammadi",
                "morteza@mail.com",
                "12345");
        successResponse = AuthenticationResponse.builder()
                .accessToken("access Token")
                .refreshToken("refresh Token")
                .httpStatus(HttpStatus.CREATED)
                .build();
        authenticationRequest = new AuthenticationRequest("morteza@mail.com", "12345");
    }

    @Test
    public void testRegister_Success() throws Exception {
        Mockito.when(authenticationService.register(validRequest)).thenReturn(successResponse);
        mockMvc.perform(post("/api/v1/auth/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticate_Success() throws Exception {
        Mockito.when(authenticationService.authenticate(authenticationRequest)).thenReturn(successResponse);
        mockMvc.perform(post("/api/v1/auth/authenticate"))
                .andExpect(status().isOk());
    }
}