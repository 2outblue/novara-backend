package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.config.JWKAlgorithmImpl;
import com.novaraspace.model.domain.PassResetEmailParams;
import com.novaraspace.model.dto.auth.AuthResponseDTO;
import com.novaraspace.model.dto.auth.TokenAuthenticationDTO;
import com.novaraspace.model.dto.auth.VerificationTokenDTO;
import com.novaraspace.model.dto.user.PasswordResetRequestDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.PasswordResetToken;
import com.novaraspace.model.entity.RefreshToken;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.ErrCode;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.enums.audit.PassEventType;
import com.novaraspace.model.events.PasswordEvent;
import com.novaraspace.model.events.UserLoginEvent;
import com.novaraspace.model.events.UserLogoutEvent;
import com.novaraspace.model.events.UserRegisterEvent;
import com.novaraspace.model.exception.*;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
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
    private final PasswordResetTokenService passwordResetService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthService(
            JwtEncoder jwtEncoder, UserService userService, VerificationService verificationService,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder, UserMapper userMapper, PasswordResetTokenService passwordResetService,
            EmailService emailService, ApplicationEventPublisher eventPublisher, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.verificationService = verificationService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }


    @Transactional
    public VerificationTokenDTO registerUser(UserRegisterDTO dto) {
//        User newUser = userMapper.registerToUser(dto); //Passwords are hashed in the mapper
        User newUser = userService.createUser(dto);
        VerificationToken verificationToken = verificationService.generateVerificationToken(dto.getEmail());
        if (emailVerificationEnabled) {
            newUser.setVerification(verificationToken);
        }
//        userService.persistUser(newUser);
        eventPublisher.publishEvent(new UserRegisterEvent(newUser.getId(), newUser.getEmail()));
        return new VerificationTokenDTO()
                .setEmail(newUser.getEmail())
                .setCode(verificationToken.getCode())
                .setLinkToken(verificationToken.getLinkToken());
    }

    @Transactional
    public AuthResponseDTO login(Authentication auth) {
        TokenAuthenticationDTO tokenDTO = generateNewTokenAuthentication(auth);
        ResponseCookie cookie = createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        eventPublisher.publishEvent(new UserLoginEvent(Outcome.SUCCESS, auth.getName()));
        return new AuthResponseDTO().setJwt(tokenDTO.getJwt()).setCookie(cookie);
    }

    public AuthResponseDTO refresh(String refreshToken) {
        TokenAuthenticationDTO tokenDTO = validateRefreshToken(refreshToken);
        ResponseCookie cookie = createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        return new AuthResponseDTO().setJwt(tokenDTO.getJwt()).setCookie(cookie);
    }

    public ResponseCookie logout(String refreshToken) {
        String publicKey = getRefreshTokenParams(refreshToken)[0];
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByPublicKey(publicKey)
                .orElseThrow(RefreshTokenException::invalid);

        Optional<User> user = userService.findEntityByAuthId(refreshTokenEntity.getUserAuthId());
        user.ifPresent(u -> eventPublisher.publishEvent(new UserLogoutEvent(u.getId(), u.getEmail())));

        invalidateTokenFamily(refreshTokenEntity.getFamilyId());
        return createRefreshTokenCookie("", true);
    }

    public void verifyAccountByLinkTokenOrCode(String linkOrCode) {
        VerificationToken verification = verificationService.getEntityByLinkTokenOrCode(linkOrCode);
        if (verification.getExpiresAt().isBefore(Instant.now()) || verification.isUsed()) {
            throw VerificationException.disabled(); //TODO: Don't throw .disabled() anywhere - just failed.
        }
        User user = userService.getEntityByEmail(verification.getUserEmail()).orElseThrow(VerificationException::failed);
        if (!user.getStatus().equals(AccountStatus.PENDING_ACTIVATION)) {throw VerificationException.disabled();}
        userService.activateUserAccount(verification.getUserEmail());
    }



    public VerificationTokenDTO generateNewVerification(String email) {
        // Could return a 200 here and in verifyAccountByLinkTokenOrCode()
        // in order to not expose any implementation details and accounts
        // with outcome PENDING_ACTIVATION and also standardize response times
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

    @Transactional
    public void sendPasswordResetLink(String email) {
        User user = this.userService.getEntityByEmail(email).orElse(null);
        if (user == null || !user.isActive() || user.isDemo()) {
            return;
        }
        PasswordResetToken token = this.passwordResetService.generateNewToken(user.getAuthId());
        PassResetEmailParams emailParams = new PassResetEmailParams(
                email,
                token.getTokenValue(),
                user.getFirstName(),
                "30"
        );
        emailService.sendPasswordResetLink(emailParams);
        eventPublisher.publishEvent(new PasswordEvent(PassEventType.RESET_REQUEST, email));
    }

    @Transactional
    public void resetUserPassword(PasswordResetRequestDTO dto) {
        Optional<PasswordResetToken> token = passwordResetService.findAndDeleteTokenByValue(dto.getResetToken());
        if (token.isEmpty() || token.get().getExpiresOn().isBefore(LocalDateTime.now())) {
            throw new UserException(ErrCode.RESET_TOKEN_INVALID, HttpStatus.BAD_REQUEST, "Invalid token.");
        }
        User user = userService.findEntityByAuthId(token.get().getUserAuthId()).orElse(null);
        if (user == null || !user.isActive() || user.isDemo()) {
            throw UserException.updateFailed();
        }
        invalidateAllUserSessions(user.getAuthId());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        eventPublisher.publishEvent(new PasswordEvent(PassEventType.CHANGE, user.getEmail()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken, boolean logout) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge( logout ? Duration.ZERO : Duration.ofHours(refreshExpiryHours))
                .sameSite("Strict")
                .build();
    }

    public TokenAuthenticationDTO generateNewTokenAuthentication(Authentication authentication) {
        String authId = userService.getEntityByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials.")).getAuthId();

        String refreshToken = generateNewRefreshToken(authId);
        String jwt = generateJwtByAuthId(authId);
        return new TokenAuthenticationDTO(refreshToken, jwt);
    }

    public void invalidateAllUserSessions(String userAuthId) {
        if (userAuthId == null) { return; }
        refreshTokenRepository.revokeByUserAuthId(userAuthId);

    }

//    public void invalidateActiveTokens(String rawRefreshToken) {
//        //There shouldn't be any active tokens aside from the input one, but for safety invalidate by familyId.
//        String publicKey = getRefreshTokenParams(rawRefreshToken)[0];
//        RefreshToken refreshTokenEntity = refreshTokenRepository.findByPublicKey(publicKey)
//                .orElse(null);
//        if (refreshTokenEntity != null) {
//            invalidateTokenFamily(refreshTokenEntity.getFamilyId());
//        }
//    }

    private TokenAuthenticationDTO validateRefreshToken(String rawRefreshToken) {
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

        if (!userEntity.isActive()) {
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
