package com.narcis.devpath.authenticationservice.repository;

import com.narcis.devpath.authenticationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
