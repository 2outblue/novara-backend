package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.config.JWKAlgorithmImpl;
import com.novaraspace.model.domain.RefreshTokenParams;
import com.novaraspace.model.dto.auth.TokenAuthenticationDTO;
import com.novaraspace.model.entity.RefreshToken;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.exception.RefreshTokenException;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    @Value("${app.jwt.issuer}")
    private String issuer;
    @Value("${app.jwt.expiry-minutes}")
    private long jwtExpiryMinutes;
    @Value("${app.jwt.refresh-expiry-hours}")
    private long refreshExpiryHours;

    public AuthTokenService(RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    public TokenAuthenticationDTO rotateRefreshToken(String rawRefreshToken) {
        RefreshTokenParams tokenParams = getRefreshTokenParams(rawRefreshToken)
                .orElseThrow(RefreshTokenException::invalid);
        String publicKey = tokenParams.publicKey();
        String rawToken = tokenParams.rawToken();

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByPublicKey(publicKey)
                .orElseThrow(RefreshTokenException::invalid);

        if (refreshTokenEntity.isExpired()) {
            throw RefreshTokenException.expired();
        }

        if (refreshTokenEntity.maybeBenignReuse()) {
            throw RefreshTokenException.conflict();
        } else if (refreshTokenEntity.isRevoked()) {
            invalidateTokenFamily(refreshTokenEntity.getFamilyId());
            throw RefreshTokenException.invalid();
        }

        if (!passwordEncoder.matches(rawToken, refreshTokenEntity.getToken())) {
            throw RefreshTokenException.invalid();
        }

        User userEntity = userRepository.findByAuthId(refreshTokenEntity.getUserAuthId())
                .orElseThrow(RefreshTokenException::invalid);

        if (!userEntity.isActive()) {
            throw RefreshTokenException.invalid();
        }

        String newRefreshToken = refreshExistingToken(refreshTokenEntity);
        String newJwt = generateJwtByAuthId(userEntity.getAuthId());
        return new TokenAuthenticationDTO(newRefreshToken, newJwt);
    }

    public TokenAuthenticationDTO generateNewTokenAuthentication(Authentication authentication) {
        String authId = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials.")).getAuthId();

        String refreshToken = generateNewRefreshToken(authId);
        String jwt = generateJwtByAuthId(authId);
        return new TokenAuthenticationDTO(refreshToken, jwt);
    }

    private String generateJwtByAuthId(String authId) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(jwtExpiryMinutes * 60);

        User userEntity = userRepository.findByAuthId(authId)
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
        String base64Secret = getRandomBase64Secret();
        String hashedSecret = passwordEncoder.encode(base64Secret);
        String publicKey = Base64.encode(UUID.randomUUID().toString()).toString();
        String userAuthId = oldToken.getUserAuthId();
        Instant expiryDate = oldToken.getExpiryDate();
        UUID familyId = oldToken.getFamilyId();

        RefreshToken newToken = new RefreshToken(publicKey, hashedSecret, userAuthId, expiryDate, familyId);
        RefreshToken newSavedToken = refreshTokenRepository.save(newToken);

        oldToken.setRotated(newSavedToken.getPublicKey());
        refreshTokenRepository.save(oldToken);
//        refreshTokenRepository.revokeByPublicKey(oldToken.getPublicKey());
        return publicKey + "." + base64Secret;
    }

    private String generateNewRefreshToken(String authId) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds( refreshExpiryHours * 60 * 60);

        String base64Secret = getRandomBase64Secret();
        String hashedSecret = passwordEncoder.encode(base64Secret);
        String publicKey = Base64.encode(UUID.randomUUID().toString()).toString();
        UUID familyId = UUID.randomUUID();
        RefreshToken newToken = new RefreshToken(publicKey, hashedSecret, authId, expiry, familyId);

        refreshTokenRepository.save(newToken);
        return publicKey + "." + base64Secret;
    }

    private String getRandomBase64Secret() {
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[32];
        random.nextBytes(secret);
        return Base64.encode(secret).toString();
    }

    public Optional<RefreshTokenParams> getRefreshTokenParams(String rawRefreshToken) {
        if (rawRefreshToken == null || rawRefreshToken.isBlank()
                || !rawRefreshToken.contains(".") || rawRefreshToken.length() < 80) {
            return Optional.empty();
        }
        String[] tokenParams = rawRefreshToken.split("\\.");
        if (tokenParams.length != 2) { return Optional.empty(); }
        return Optional.of(new RefreshTokenParams(tokenParams[0], tokenParams[1]));
    }

    public void invalidateTokenFamily(UUID familyId) {
        refreshTokenRepository.revokeByFamilyId(familyId);
    }

    public Optional<RefreshToken> findRefreshByPublicKey(String publicKey) {
        return refreshTokenRepository.findByPublicKey(publicKey);
    }

    public void revokeTokensByUserAuthId(String userAuthId) {
        refreshTokenRepository.revokeByUserAuthId(userAuthId);
    }
}
