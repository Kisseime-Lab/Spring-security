package com.example.authentication_basic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        req -> req.requestMatchers("*/admin").hasRole("ADMIN").anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails user = userBuilder.username("toto")
                .password("toto")
                .roles("ADMIN")
                .build();
        UserDetails user2 = userBuilder.username("user")
                .password("user")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(List.of(user, user2));
    }
}
