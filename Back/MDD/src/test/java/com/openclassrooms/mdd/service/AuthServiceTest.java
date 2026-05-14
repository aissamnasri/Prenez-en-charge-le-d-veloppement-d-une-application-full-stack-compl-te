package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.auth.LoginRequest;
import com.openclassrooms.mdd.dto.auth.RegisterRequest;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.security.jwt.service.JwtService;
import com.openclassrooms.mdd.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("john");
        registerRequest.setEmail("john@test.com");
        registerRequest.setPassword("Password123!");

        LoginRequest loginRequest;
        loginRequest = new LoginRequest();
        loginRequest.setEmailOrUsername("john@test.com");
        loginRequest.setPassword("Password123!");

        User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    void register_shouldCreateUserAndReturnToken() {

        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(registerRequest.getUsername()))
                .thenReturn(false);

        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPassword");

        when(jwtService.generateToken(any(String.class)))
                .thenReturn("jwt-token");

        authService.register(registerRequest);

    }
}