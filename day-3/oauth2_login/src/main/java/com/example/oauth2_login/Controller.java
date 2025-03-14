package com.example.oauth2_login;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String secretId;

    @GetMapping("/test")
    public String getMethodName() {
        return "TEST";
    }

    @GetMapping("/home")
    public OAuth2User home(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return oAuth2User;
    }

    @GetMapping("/")
    public String get() {
        String url = "https://accounts.google.com/o/oauth2/v2/auth?" + //
                " response_type=token&" + //
                " client_id=" + clientId + "&" + //
                " scope=openid%20email&" + //
                " redirect_uri=https%3A//oauth2.example.com/code&" + //
                " state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome&" + //
                " login_hint=jsmith@example.com&" + //
                " nonce=0394852-3190485-2490358&" + //
                " hd=example.com";

        return "HOME";
    }

}
