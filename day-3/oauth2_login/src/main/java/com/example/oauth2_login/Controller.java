package com.example.oauth2_login;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

    @GetMapping("/test")
    public String getMethodName() {
        return "TEST";
    }

    @GetMapping("/home")
    public OAuth2User home(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return oAuth2User;
    }

}
