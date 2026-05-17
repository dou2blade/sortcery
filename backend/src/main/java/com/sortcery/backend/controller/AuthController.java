package com.sortcery.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sortcery.backend.dto.auth.LoginRequestDTO;
import com.sortcery.backend.dto.auth.LoginResponseDTO;
import com.sortcery.backend.model.User;
import com.sortcery.backend.service.AuthService;
import com.sortcery.backend.service.TokenService;
import com.sortcery.backend.validation.Create;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(
            AuthService authService,
            TokenService tokenService
    ) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    
    private ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody @Validated(Create.class) LoginRequestDTO request, User.Role role) {
        LoginResponseDTO authPayload = authService.authenticateUser(request.getEmail(), request.getPassword(), role);

        if (authPayload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            ResponseCookie cookie = ResponseCookie.from("api_token", authPayload.getPlainToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(60 * 60 * 3)
                .build();

            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authPayload);
        }
    }

    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponseDTO> authenticateAdmin(@RequestBody @Validated(Create.class) LoginRequestDTO request) {
        return authenticateUser(request, User.Role.ADMIN);
    }

    @PostMapping("/login/retailer")
    public ResponseEntity<LoginResponseDTO> authenticateRetailer(@RequestBody @Validated(Create.class) LoginRequestDTO request) {
        return authenticateUser(request, User.Role.RETAILER);
    }

    @PostMapping("/login/manager")
    public ResponseEntity<LoginResponseDTO> authenticateManager(@RequestBody @Validated(Create.class) LoginRequestDTO request) {
        return authenticateUser(request, User.Role.MANAGER);
    }

    @PostMapping("/login/consumer")
    public ResponseEntity<LoginResponseDTO> authenticateConsumer(@RequestBody @Validated(Create.class) LoginRequestDTO request) {
        return authenticateUser(request, User.Role.CONSUMER);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> revokeToken(HttpServletRequest request, HttpServletResponse response) {
        String plainToken = null;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            plainToken = authHeader.substring(7);
        }

        if (plainToken == null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("api_token")) {
                    plainToken = c.getValue();
                    break;
                }
            }
        }

        if (plainToken != null) {
            tokenService.revokeToken(plainToken);
        }

        ResponseCookie cookie = ResponseCookie.from("api_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/")
            .maxAge(0)
            .build();

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }
}
