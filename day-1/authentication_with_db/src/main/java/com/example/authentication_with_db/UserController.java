package com.example.authentication_with_db;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, UserService service, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "User cree";
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        repository.deleteByUsername(username);
        return "User supprimé";
    }

}
