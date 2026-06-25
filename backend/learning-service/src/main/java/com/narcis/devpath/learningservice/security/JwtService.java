package com.narcis.devpath.learningservice.security;

public interface JwtService {
    String extractUsername(String token);

    String extractRole(String token);

    boolean isTokenValid(String token);
}
