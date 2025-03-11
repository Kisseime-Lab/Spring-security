package com.example.authentication_with_db;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    void deleteByUsername(String username);

    User findByUsername(String username);
}
