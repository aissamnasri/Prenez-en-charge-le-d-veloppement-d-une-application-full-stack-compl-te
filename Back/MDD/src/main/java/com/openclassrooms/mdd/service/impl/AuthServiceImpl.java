package com.openclassrooms.mdd.service.impl;
import com.openclassrooms.mdd.dto.auth.AuthResponse;
import com.openclassrooms.mdd.dto.auth.LoginRequest;
import com.openclassrooms.mdd.dto.auth.RegisterRequest;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.security.jwt.service.JwtService;
import com.openclassrooms.mdd.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }



    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmailOrUsername())
                .orElseGet(() ->
                        userRepository.findByUsername(request.getEmailOrUsername())
                                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"))
                );

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}