package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.user.UpdateUserRequest;
import com.openclassrooms.mdd.dto.user.UserDto;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.mapper.UserMapper;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .password("encodedPassword")
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("john", null)
        );
    }

    @Test
    void getCurrentUser_shouldReturnDto() {
        UserDto dto = UserDto.builder()
                .username("john")
                .email("john@test.com")
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(userMapper.toDto(user))
                .thenReturn(dto);

        UserDto result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("john@test.com", result.getEmail());
    }

    @Test
    void updateCurrentUser_shouldUpdateAndReturnDto() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("johnny");
        request.setEmail("johnny@test.com");
        request.setPassword("NewPassword123!");

        UserDto dto = UserDto.builder()
                .username("johnny")
                .email("johnny@test.com")
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(false);
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword2");
        when(userMapper.toDto(user))
                .thenReturn(dto);

        UserDto result = userService.updateCurrentUser(request);

        assertNotNull(result);
        assertEquals("johnny", result.getUsername());
        assertEquals("johnny@test.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateCurrentUser_shouldFailWhenEmailAlreadyExists() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("johnny");
        request.setEmail("existing@test.com");
        request.setPassword("NewPassword123!");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateCurrentUser(request));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updateCurrentUser_shouldFailWhenUsernameAlreadyExists() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("existingUser");
        request.setEmail("john@test.com");
        request.setPassword("NewPassword123!");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateCurrentUser(request));

        assertEquals("Username already exists", exception.getMessage());
    }
}
