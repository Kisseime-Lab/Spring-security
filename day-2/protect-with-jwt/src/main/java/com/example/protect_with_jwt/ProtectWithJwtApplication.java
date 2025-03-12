package com.example.protect_with_jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProtectWithJwtApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(ProtectWithJwtApplication.class, args);
	}

}
