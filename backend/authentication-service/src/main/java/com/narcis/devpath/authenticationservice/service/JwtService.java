package com.narcis.devpath.authenticationservice.service;


import com.narcis.devpath.authenticationservice.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}

