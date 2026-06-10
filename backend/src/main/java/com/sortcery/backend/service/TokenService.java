package com.sortcery.backend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sortcery.backend.model.Token;
import com.sortcery.backend.model.User;
import com.sortcery.backend.repository.TokenRepository;
import com.sortcery.backend.security.TokenHasher;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenHasher hasher;

    public TokenService(TokenRepository tokenRepository, TokenHasher hasher) {
        this.tokenRepository = tokenRepository;
        this.hasher = hasher;
    }

    public String createToken(User user) {
        String plain = UUID.randomUUID().toString();
        String lookup = hasher.sha256(plain);
        String hash = hasher.bcrypt(plain);
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(3);

        Token token = new Token(user, lookup, hash, expiresAt);
        tokenRepository.save(token);

        return plain;
    }

    public Token validateToken(String plainToken) {
        String lookup = hasher.sha256(plainToken);
        Token token = tokenRepository.findBySha256(lookup);
        if (token != null && hasher.matches(plainToken, token.getBcrypt())) {
            if (token.getExpiresAt() == null 
                || token.getExpiresAt().isAfter(LocalDateTime.now())
            ) {
                return token;
            }
        }
        return null;
    }

    public Token revokeToken(String plainToken) {
        String lookup = hasher.sha256(plainToken);
        Token token = tokenRepository.findBySha256(lookup);
        
        tokenRepository.delete(token);

        return token;
    }

    public void refreshIfNeeded(Token token) {
        LocalDateTime threshold = LocalDateTime.now().plusHours(1);

        if (token.getExpiresAt().isBefore(threshold)) {
            token.setExpiresAt(LocalDateTime.now().plusHours(3));
        }
    }
}
