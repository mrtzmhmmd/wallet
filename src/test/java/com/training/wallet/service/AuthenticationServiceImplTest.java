package com.training.wallet.service;

import com.training.wallet.domain.enums.Role;
import com.training.wallet.domain.model.User;
import com.training.wallet.dto.request.RegisterRequest;
import com.training.wallet.repository.TokenRepository;
import com.training.wallet.repository.UserRepository;
import com.training.wallet.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
did not work correctly
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TokenRepository tokenRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthenticationServiceImpl service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrSaveEmployee() {
        RegisterRequest request = new RegisterRequest("Morteza", "Mohammadi", "morteza@mail.com", "12345");
        service.register(request);
        User user = new User(1L,
                "Morteza",
                "Mohammadi",
                "morteza@mail.com",
                "12345",
                Role.USER);
        verify(userRepository, times(1)).save(user);
    }
}