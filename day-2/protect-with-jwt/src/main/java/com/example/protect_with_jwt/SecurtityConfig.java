package com.example.protect_with_jwt;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.Jwts;

@EnableWebSecurity
@Configuration
public class SecurtityConfig {

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 min
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 jours

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(req -> req.requestMatchers("/*/create").permitAll()
                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }

    private RSAPublicKey publicKey() throws Exception {
        return (RSAPublicKey) loadPublicKey("src/main/resources/keys/public.pem");
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        // Lire le contenu du fichier public.pem
        String key = new String(Files.readAllBytes(Paths.get("src/main/resources/keys/private.pem")));

        // Supprimer les en-têtes et pieds de la clé
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // Décoder la clé en Base64
        byte[] keyBytes = Base64.getDecoder().decode(key);

        // Construire une clé RSA publique
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(String filePath) throws Exception {
        // Lire le contenu du fichier public.pem
        String key = new String(Files.readAllBytes(Paths.get(filePath)));

        // Supprimer les en-têtes et pieds de la clé
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // Supprimer les espaces et sauts de ligne

        // Décoder la clé en Base64
        byte[] keyBytes = Base64.getDecoder().decode(key);

        // Construire une clé RSA publique
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static Map<String, String> generateTokens(String username) throws Exception {
        long now = System.currentTimeMillis();
        PrivateKey privateKey = loadPrivateKey();
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRATION))
                .signWith(privateKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION))
                .signWith(privateKey)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

}
