package com.training.wallet.controller;

import com.training.wallet.dto.request.AuthenticationRequest;
import com.training.wallet.dto.request.RegisterRequest;
import com.training.wallet.dto.response.AuthenticationResponse;
import com.training.wallet.service.AuthenticationService;
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
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService authenticationService;

    private RegisterRequest validRequest;
    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse successResponse;
    private AuthenticationResponse emailExistResponse;

    @BeforeEach
    public void setUp() {
        validRequest = new RegisterRequest("Morteza",
                "Mohammadi",
                "morteza@mail.com",
                "12345");
        authenticationRequest = new AuthenticationRequest("morteza@mail.com", "12345");
        successResponse = AuthenticationResponse.builder()
                .accessToken("access Token")
                .refreshToken("refresh Token")
                .httpStatus(HttpStatus.CREATED)
                .build();

        emailExistResponse = AuthenticationResponse.builder()
                .message("Email already exists")
                .httpStatus(HttpStatus.CONFLICT)
                .build();
    }

    @Test
    public void testRegisterSuccess() {
        when(authenticationService.register(validRequest)).thenReturn(successResponse);
        ResponseEntity<?> response = authenticationController.register(validRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRegisterEmailExists() {
        when(authenticationService.register(validRequest)).thenReturn(emailExistResponse);
        ResponseEntity<?> response = authenticationController.register(validRequest);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testAuthenticate() {
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(successResponse);
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}