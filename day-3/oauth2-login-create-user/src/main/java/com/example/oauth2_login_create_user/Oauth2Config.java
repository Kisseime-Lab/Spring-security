package com.example.oauth2_login_create_user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Oauth2Config {

    @Bean
    SecurityFilterChain sFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(f -> f.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/login",
                                "/oauth2/authorization/google")
                        .permitAll().anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/home"));
        return http.build();
    }
}
