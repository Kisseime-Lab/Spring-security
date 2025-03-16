package com.example.oauth2_client_get_github_repo;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class Controller {
    private final RestClient restClient;

    public Controller(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/home")
    public OAuth2User home(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        return user;
    }

    @GetMapping("/repos")
    public ResponseEntity<List<Object>> repos() {
        Object[] repos = restClient.get()
                .uri("https://api.github.com/users/Kyse7535/repos")
                .retrieve()
                .body(Object[].class);

        return ResponseEntity.ok(Arrays.asList(repos));
    }

}
