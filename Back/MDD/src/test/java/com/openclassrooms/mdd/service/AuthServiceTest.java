package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.auth.AuthResponse;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    }

    @Test
    void register_shouldCreateUserAndReturnToken() {
        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername()))
                .thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPassword");
        when(jwtService.generateToken(registerRequest.getUsername()))
                .thenReturn("jwt-token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldFailWhenEmailExists() {
        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(registerRequest));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void register_shouldFailWhenUsernameExists() {
        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(registerRequest));

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void login_shouldReturnTokenWhenEmail() {
        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john@test.com");
        request.setPassword("Password123!");

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);
        when(jwtService.generateToken(user.getUsername()))
                .thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void login_shouldReturnTokenWhenUsername() {
        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john");
        request.setPassword("Password123!");

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail("john"))
                .thenReturn(Optional.empty());
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);
        when(jwtService.generateToken(user.getUsername()))
                .thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void login_shouldFailWhenPasswordInvalid() {
        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john@test.com");
        request.setPassword("wrong");

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.login(request));
    }

    @Test
    void login_shouldFailWhenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("unknown");
        request.setPassword("Password123!");

        when(userRepository.findByEmail("unknown"))
                .thenReturn(Optional.empty());
        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,
                () -> authService.login(request));
    }
}
