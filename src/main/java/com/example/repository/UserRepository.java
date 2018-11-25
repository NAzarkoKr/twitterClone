package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.SecureRandom;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
