package com.novaraspace.web;

import com.novaraspace.model.dto.auth.TokenAuthenticationDTO;
import com.novaraspace.model.dto.user.UserLoginDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.service.AuthService;
import com.novaraspace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthService authService;
    private final UserService userService;

    @Value("${app.jwt.expiry-minutes}")
    private long jwtExpiryMinutes;

    public AuthController(AuthenticationManager authManager, AuthService authService, UserService userService) {
        this.authManager = authManager;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterDTO userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<OAuth2AccessTokenResponse> login(@RequestBody UserLoginDTO dto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        
        TokenAuthenticationDTO tokenDTO = authService.generateNewTokenAuthentication(authentication);
        ResponseCookie cookie = authService.createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(OAuth2AccessTokenResponse
                        .withToken(tokenDTO.getJwt())
                        .expiresIn(jwtExpiryMinutes * 60)
                        .tokenType(OAuth2AccessToken.TokenType.BEARER)
                        .build()
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<OAuth2AccessTokenResponse> refresh(@CookieValue String refreshToken) {
        TokenAuthenticationDTO tokenDTO = authService.validateRefreshToken(refreshToken);
        ResponseCookie cookie = authService.createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(OAuth2AccessTokenResponse
                        .withToken(tokenDTO.getJwt())
                        .expiresIn(jwtExpiryMinutes * 60)
                        .tokenType(OAuth2AccessToken.TokenType.BEARER)
                        .build()
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue String refreshToken) {
        ResponseCookie invalidCookie = authService.createRefreshTokenCookie("", true);
        authService.invalidateActiveTokens(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, invalidCookie.toString())
                .build();
    }
}

