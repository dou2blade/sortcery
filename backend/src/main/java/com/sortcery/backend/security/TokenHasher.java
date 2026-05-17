package com.sortcery.backend.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TokenHasher {
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public String sha256(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException error) {
            throw new IllegalStateException("SHA-256 not available", error);
        }
    }

    public String bcrypt(String token) {
        return bCryptEncoder.encode(token);
    }

    public boolean matches(String token, String bCryptHash) {
        return bCryptEncoder.matches(token, bCryptHash);
    }
}
