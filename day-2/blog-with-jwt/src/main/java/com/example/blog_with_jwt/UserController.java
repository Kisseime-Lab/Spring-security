package com.example.blog_with_jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    private @PostMapping SignedUser signin(@RequestBody User entity) throws Exception {
        return service.authenticate(entity);
    }

    @PostMapping("/sign-up")
    public String signup(@RequestBody User entity) {
        service.create(entity);
        return "user cree";
    }

    @ExceptionHandler(value = Exception.class)
    public String hadnler(Exception e) {
        return e.getMessage();
    }

}
