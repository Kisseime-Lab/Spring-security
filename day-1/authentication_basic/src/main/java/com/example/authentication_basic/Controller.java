package com.example.authentication_basic;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/test")
public class Controller {

    @GetMapping
    public String getMethodName() {
        return "USER";
    }

    @GetMapping("/admin")
    public String frAdmin() {
        return "ADMIN";
    }

}
