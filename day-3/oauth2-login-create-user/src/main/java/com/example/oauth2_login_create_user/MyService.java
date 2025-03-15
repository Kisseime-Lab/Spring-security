package com.example.oauth2_login_create_user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final SecurityFilterChain sFilterChain;

    private Map<UUID, User> registry = new HashMap<>();

    MyService(SecurityFilterChain sFilterChain) {
        this.sFilterChain = sFilterChain;
    }

    public void createUser(String name, String picture) {
        if (registry.values().stream().anyMatch(u -> u.username().equals(name))) {
            return;
        }
        User user = new User(UUID.randomUUID(), name);
        registry.putIfAbsent(user.id(), user);
    }

    public Optional<User> getUser(String name) {
        return registry.values().stream().filter(u -> u.username().equals(name)).findFirst();
    }
}
