package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.config.JWKAlgorithmImpl;
import com.novaraspace.model.dto.auth.TokenAuthenticationDTO;
import com.novaraspace.model.dto.auth.VerificationTokenDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.RefreshToken;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.ErrCode;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.exception.*;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {
    @Value("${app.jwt.issuer}")
    private String issuer;
    @Value("${app.jwt.expiry-minutes}")
    private long jwtExpiryMinutes;
    @Value("${app.jwt.refresh-expiry-hours}")
    private long refreshExpiryHours;
    @Value("${app.enable-email-verification}")
    private boolean emailVerificationEnabled;

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final VerificationService verificationService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthService(
            JwtEncoder jwtEncoder, UserService userService, VerificationService verificationService,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder, UserMapper userMapper
    ) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.verificationService = verificationService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public VerificationTokenDTO registerUser(UserRegisterDTO dto) {
        User newUser = userMapper.registerToUser(dto); //Passwords are hashed in the mapper
        VerificationToken verificationToken = verificationService.generateVerificationToken(dto.getEmail());
        if (emailVerificationEnabled) {
            newUser.setVerification(verificationToken);
        }
        userService.persistUser(newUser);
        return new VerificationTokenDTO()
                .setEmail(newUser.getEmail())
                .setCode(verificationToken.getCode())
                .setLinkToken(verificationToken.getLinkToken());
    }

    public void verifyAccountByLinkTokenOrCode(String linkOrCode) {
        VerificationToken verification = verificationService.getEntityByLinkTokenOrCode(linkOrCode);
        if (verification.getExpiresAt().isBefore(Instant.now()) || verification.isUsed()) {
            throw VerificationException.disabled();
        }
        User user = userService.getEntityByEmail(verification.getUserEmail()).orElseThrow(VerificationException::failed);
        if (!user.getStatus().equals(AccountStatus.PENDING_ACTIVATION)) {throw VerificationException.disabled();}
        userService.activateUserAccount(verification.getUserEmail());
    }
    public VerificationTokenDTO generateNewVerification(String email) {
        // Could return a 200 here and in verifyAccountByLinkTokenOrCode()
        // in order to not expose any implementation details and accounts
        // with status PENDING_ACTIVATION and also standardize response times
        // but thats an overkill for this project at this stage...
        if (!emailVerificationEnabled) {throw new FailedOperationException();}

        User user = userService.getEntityByEmail(email).orElseThrow(VerificationException::failed);
        if (!user.getStatus().equals(AccountStatus.PENDING_ACTIVATION)) {throw VerificationException.failed();}
        VerificationToken newVerification = verificationService.generateVerificationToken(email);
        userService.updateUserVerification(email, newVerification);
        return new VerificationTokenDTO()
                .setEmail(user.getEmail())
                .setCode(newVerification.getCode())
                .setLinkToken(newVerification.getLinkToken());
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken, boolean logout) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge( logout ? Duration.ZERO : Duration.ofHours(refreshExpiryHours))
                .sameSite("Strict")
                .build();
    }

    public TokenAuthenticationDTO generateNewTokenAuthentication(Authentication authentication) {
        String authId = userService.getAuthIdByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials."));

        String refreshToken = generateNewRefreshToken(authId);
        String jwt = generateJwtByAuthId(authId);
        return new TokenAuthenticationDTO(refreshToken, jwt);
    }

    public void invalidateActiveTokens(String rawRefreshToken) {
        //There shouldn't be any active tokens aside from the input one, but for safety invalidate by familyId.
        String publicKey = getRefreshTokenParams(rawRefreshToken)[0];
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByPublicKey(publicKey)
                .orElseThrow(RefreshTokenException::invalid);
        invalidateTokenFamily(refreshTokenEntity.getFamilyId());
    }

    public TokenAuthenticationDTO validateRefreshToken(String rawRefreshToken) {
        String[] tokenParams = getRefreshTokenParams(rawRefreshToken);
        String publicKey = tokenParams[0];
        String rawToken = tokenParams[1];

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByPublicKey(publicKey)
                .orElseThrow(RefreshTokenException::invalid);

        if (refreshTokenEntity.isRevoked()) {
            invalidateTokenFamily(refreshTokenEntity.getFamilyId());
            throw RefreshTokenException.invalid();
        }

        if (!passwordEncoder.matches(rawToken, refreshTokenEntity.getToken())) {
            throw RefreshTokenException.invalid();
        }

        if (refreshTokenEntity.getExpiryDate().isBefore(Instant.now())) {
            throw RefreshTokenException.expired();
        }

        User userEntity = userService.findEntityByAuthId(refreshTokenEntity.getUserAuthId())
                .orElseThrow(RefreshTokenException::invalid);

        if (!userEntity.getStatus().equals(AccountStatus.ACTIVE)) {
            throw RefreshTokenException.invalid();
        }

        String newRefreshToken = refreshExistingToken(refreshTokenEntity);
        String newJwt = generateJwtByAuthId(userEntity.getAuthId());
        return new TokenAuthenticationDTO(newRefreshToken, newJwt);
    }

    private String generateJwtByAuthId(String authId) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(jwtExpiryMinutes * 60);

        User userEntity = userService.findEntityByAuthId(authId)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials."));

        Set<UserRole> roles = userEntity.getRoles();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(authId)
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader.with(new JWKAlgorithmImpl())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    private String refreshExistingToken(RefreshToken oldToken) {
        String encodedSecret = getRandomBase64Secret();
        String encryptedSecret = passwordEncoder.encode(encodedSecret);
        String publicKey = Base64.encode(UUID.randomUUID().toString()).toString();
        String userAuthId = oldToken.getUserAuthId();
        Instant expiryDate = oldToken.getExpiryDate();
        UUID familyId = oldToken.getFamilyId();

        RefreshToken newToken = new RefreshToken(publicKey, encryptedSecret, userAuthId, expiryDate, familyId);
        refreshTokenRepository.save(newToken);
        refreshTokenRepository.revokeByPublicKey(oldToken.getPublicKey());
        return publicKey + "." + encodedSecret;
    }

    private String generateNewRefreshToken(String authId) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds( refreshExpiryHours * 60 * 60);

        String encodedSecret = getRandomBase64Secret();
        String encryptedSecret = passwordEncoder.encode(encodedSecret);
        String publicKey = Base64.encode(UUID.randomUUID().toString()).toString();
        UUID familyId = UUID.randomUUID();
        RefreshToken newToken = new RefreshToken(publicKey, encryptedSecret, authId, expiry, familyId);

        refreshTokenRepository.save(newToken);
        return publicKey + "." + encodedSecret;
    }

    private String getRandomBase64Secret() {
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[32];
        random.nextBytes(secret);
        return Base64.encode(secret).toString();
    }

    private String[] getRefreshTokenParams(String rawRefreshToken) {
        if (rawRefreshToken == null || rawRefreshToken.isBlank() || !rawRefreshToken.contains(".")) {
            throw RefreshTokenException.invalid();
        }
        String[] tokenParams = rawRefreshToken.split("\\.");
        if (tokenParams.length != 2) {throw RefreshTokenException.invalid();}
        return tokenParams;
    }

    private void invalidateTokenFamily(UUID familyId) {
        refreshTokenRepository.revokeByFamilyId(familyId);
    }
}
