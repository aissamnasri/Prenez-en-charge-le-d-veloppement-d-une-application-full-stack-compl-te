package com.openclassrooms.mdd.service.impl;

import com.openclassrooms.mdd.dto.user.UpdateUserRequest;
import com.openclassrooms.mdd.dto.user.UserDto;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.mapper.UserMapper;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getCurrentUser() {

        User user = getAuthenticatedUser();

        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateCurrentUser(UpdateUserRequest request) {

        User user = getAuthenticatedUser();

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        if (!user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {

            throw new RuntimeException("Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}