package com.example.protect_with_jwt;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/day-2")
public class Controller {
    @GetMapping
    public String getMethodName() {
        return "Ressource protégée";
    }

    @GetMapping("/create")
    public Result postMethodName() throws Exception {
        // TODO: process POST request
        Map<String, String> tokens = SecurtityConfig.generateTokens("Kisseime");
        return new Result(tokens.get("accessToken"), tokens.get("refreshToken"));
    }

    private class Result {
        private String accessToken;
        private String refreshToken;

        public Result() {
        }

        public Result(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

    }

}
