package com.novaraspace.web;

import com.novaraspace.model.dto.auth.TokenAuthenticationDTO;
import com.novaraspace.model.dto.user.UserLoginDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.service.AuthService;
import com.novaraspace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(OAuth2AccessTokenResponse
                .withToken(tokenDTO.getJwt())
                .expiresIn(jwtExpiryMinutes * 60)
                .refreshToken(tokenDTO.getRefreshToken())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .build()
        );
    }
}

