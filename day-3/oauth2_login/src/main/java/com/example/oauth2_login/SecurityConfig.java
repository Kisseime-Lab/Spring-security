package com.example.oauth2_login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final Oauth2LoginApplication oauth2LoginApplication;

    SecurityConfig(Oauth2LoginApplication oauth2LoginApplication) {
        this.oauth2LoginApplication = oauth2LoginApplication;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(f -> f.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/login",
                                "/oauth2/authorization/google")
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(req -> req.defaultSuccessUrl("/home", true));
        return http.build();
    }
}
