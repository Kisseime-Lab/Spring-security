package com.example.oauth2_login_create_user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

    private final MyService service;

    public Controller(MyService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String home(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        service.createUser(user.getAttribute("name"), user.getAttribute("picture"));
        return "Connected as " + service.getUser(user.getAttribute("name")).get();
        // return new String();
    }

    @GetMapping("/resources")
    public String resources() {
        return "Protected resources";
    }

}
