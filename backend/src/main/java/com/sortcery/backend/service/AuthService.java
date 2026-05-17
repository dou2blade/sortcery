package com.sortcery.backend.service;

import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.auth.LoginResponseDTO;
import com.sortcery.backend.repository.UserRepository;
import com.sortcery.backend.exception.UnauthorizedException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public AuthService(
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO authenticateUser(String email, String password, User.Role role) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("Incorrect credentials"));

        if (user.getRole() != role) {
            throw new UnauthorizedException("Incorrect credentials");
        }

        if (bCryptEncoder.matches(password, user.getPassword())) {
            return new LoginResponseDTO(user, tokenService.createToken(user));
        } else {
            throw new UnauthorizedException("Incorrect credentials");
        }
    }
}
