package com.openclassrooms.mdd.security.jwt.service;

public interface JwtService {

    String generateToken(String username);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}