package com.narcis.devpath.authenticationservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthorizationController {

    @GetMapping("/auth/test/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String userEndpoint() {
        return "USER or ADMIN endpoint";
    }

    @GetMapping("/auth/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "ADMIN endpoint";
    }

    @GetMapping("/auth/test/authenticated")
    @PreAuthorize("isAuthenticated()")
    public String authenticatedEndpoint() {
        return "Authenticated endpoint";
    }
}
