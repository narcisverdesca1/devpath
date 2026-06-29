package com.narcis.devpath.noteservice.security;

public interface JwtService {
    String extractUsername(String token);

    String extractRole(String token);

    boolean isTokenValid(String token);
}
