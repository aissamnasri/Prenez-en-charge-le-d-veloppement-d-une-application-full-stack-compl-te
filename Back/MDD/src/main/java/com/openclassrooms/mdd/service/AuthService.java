package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.auth.AuthResponse;
import com.openclassrooms.mdd.dto.auth.LoginRequest;
import com.openclassrooms.mdd.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}