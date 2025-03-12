package com.example.blog_with_jwt;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public SignedUser authenticate(User user) throws Exception {
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        if (userDetails == null) {
            throw new RuntimeException("Utilisateur non trouve");
        }
        if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            List<String> authorities = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList();
            Map<String, String> tokens = SecureConfig.generateTokens(user.getUsername(),
                    authorities);
            SignedUser signedUser = new SignedUser(user.getUsername(), tokens.get("accessToken"),
                    tokens.get("refreshToken"));
            return signedUser;
        }
        throw new RuntimeException("Username et/ou Password incorrect");
    }

}
