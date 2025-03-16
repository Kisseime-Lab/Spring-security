package com.example.oauth2_client_get_github_repo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor.ClientRegistrationIdResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@EnableWebSecurity
@Configuration
public class Config {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .formLogin(f -> f.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/login", "/logout", "/oauth2/code/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(req -> req.defaultSuccessUrl("/home"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor = new OAuth2ClientHttpRequestInterceptor(
                authorizedClientManager);
        requestInterceptor.setClientRegistrationIdResolver(clientRegistrationIdResolver());

        return RestClient.builder()
                .requestInterceptor(requestInterceptor)
                .build();
    }

    private static ClientRegistrationIdResolver clientRegistrationIdResolver() {
        return (request) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (authentication instanceof OAuth2AuthenticationToken principal)
                    ? principal.getAuthorizedClientRegistrationId()
                    : null;
        };
    }
}
