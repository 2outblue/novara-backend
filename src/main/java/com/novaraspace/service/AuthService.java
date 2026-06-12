package com.novaraspace.service;

import com.novaraspace.model.domain.PassResetEmailParams;
import com.novaraspace.model.domain.RefreshTokenParams;
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
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.enums.audit.PassEventType;
import com.novaraspace.model.events.PasswordEvent;
import com.novaraspace.model.events.UserLoginEvent;
import com.novaraspace.model.events.UserLogoutEvent;
import com.novaraspace.model.events.UserRegisterEvent;
import com.novaraspace.model.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
public class AuthService {
    private final EmailService emailService;
    @Value("${app.jwt.refresh-expiry-hours}")
    private long refreshExpiryHours;
    @Value("${app.enable-email-verification}")
    private boolean emailVerificationEnabled;
    @Value("${app.user.max-activation-attempts}")
    private int maxUserActivationAttempts;
    @Value("${app.user.reset-token-expiry-minutes}")
    private int resetTokenExpiryMinutes;

    private final UserService userService;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthTokenService tokenService;

    public AuthService(
            UserService userService, VerificationService verificationService,
            PasswordEncoder passwordEncoder, PasswordResetTokenService passwordResetService,
            EmailService emailService, ApplicationEventPublisher eventPublisher,
            AuthTokenService tokenService) {
        this.userService = userService;
        this.verificationService = verificationService;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
        this.eventPublisher = eventPublisher;
        this.tokenService = tokenService;
    }


    @Transactional
    public void registerUser(UserRegisterDTO dto) {
        User newUser = userService.createUser(dto);
        VerificationToken verificationToken = verificationService.generateTokenForUserRegister(dto.getEmail());
        if (emailVerificationEnabled) {
            newUser.setVerification(verificationToken);
            VerificationTokenDTO verificationDTO = new VerificationTokenDTO(
                    newUser.getEmail(), verificationToken.getCode(), verificationToken.getLinkToken());
            emailService.sendActivationEmail(verificationDTO);
        }

        eventPublisher.publishEvent(new UserRegisterEvent(newUser.getId(), newUser.getEmail()));
    }

    @Transactional
    public AuthResponseDTO login(Authentication auth) {
        TokenAuthenticationDTO tokenDTO = tokenService.generateNewTokenAuthentication(auth);
        ResponseCookie cookie = createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        eventPublisher.publishEvent(new UserLoginEvent(Outcome.SUCCESS, auth.getName()));
        return new AuthResponseDTO().setJwt(tokenDTO.getJwt()).setCookie(cookie);
    }

    public AuthResponseDTO refresh(String refreshToken) {
        TokenAuthenticationDTO tokenDTO = tokenService.rotateRefreshToken(refreshToken);

        ResponseCookie cookie = createRefreshTokenCookie(tokenDTO.getRefreshToken(), false);
        return new AuthResponseDTO().setJwt(tokenDTO.getJwt()).setCookie(cookie);
    }

    public ResponseCookie logout(String rawToken) {
        RefreshTokenParams tokenParams = tokenService.getRefreshTokenParams(rawToken).orElse(null);
        if (tokenParams == null) {
            return createRefreshTokenCookie("", true);
        }

        RefreshToken refreshTokenEntity = tokenService.findRefreshByPublicKey(tokenParams.publicKey())
                .orElse(null);

        if (refreshTokenEntity != null) {
            tokenService.invalidateTokenFamily(refreshTokenEntity.getFamilyId());
            Optional<User> user = userService.findEntityByAuthId(refreshTokenEntity.getUserAuthId());
            user.ifPresent(u -> eventPublisher.publishEvent(new UserLogoutEvent(u.getId(), u.getEmail())));
        }

        return createRefreshTokenCookie("", true);
    }

    @Transactional
    public void verifyAccountByLinkTokenOrCode(String linkOrCode) {
        VerificationToken verification = verificationService.getEntityByLinkTokenOrCode(linkOrCode);
        if (verification == null || verification.isInvalid()) {
            return;
        }
        User user = userService.getEntityByEmail(verification.getUserEmail()).orElse(null);
        if (user == null || !user.isPendingActivation() || user.isDemo()) {
            return;
        }
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerification(null);
    }


    @Transactional
    public void retryAccountActivation(String email) {
        if (!emailVerificationEnabled) {throw new FailedOperationException();}

        User user = userService.getEntityByEmail(email).orElse(null);
        if (user == null
                || !user.isPendingActivation()
                || user.isDemo()) {
            return;
        }
        VerificationToken existingVerification = user.getVerification();
        if (existingVerification == null || existingVerification.getSerialNumber() > maxUserActivationAttempts) {
            return;
        }

        VerificationToken newVerification = verificationService.generateTokenForRetryActivation(existingVerification);
        boolean emailSend = emailService.sendActivationEmail(new VerificationTokenDTO(
                user.getEmail(), newVerification.getCode(), newVerification.getLinkToken()
        ));
        if (emailSend) {
            verificationService.deleteAllExistingTokensForEmail(user.getEmail());
            user.setVerification(newVerification);
        }
    }

    @Transactional
    public void sendPasswordResetLink(String email) {
        User user = this.userService.getEntityByEmail(email).orElse(null);
        if (user == null || !user.isActive() || user.isDemo()) {
            return;
        }
        boolean resetPossible = user.acquireResetPasswordSlot();
        if (!resetPossible) { return; }

        PasswordResetToken token = this.passwordResetService.generateNewToken(user.getAuthId());
        PassResetEmailParams emailParams = new PassResetEmailParams(
                email,
                token.getTokenValue(),
                user.getFirstName(),
                Integer.toString(resetTokenExpiryMinutes)
        );
        emailService.sendPasswordResetLink(emailParams);
        eventPublisher.publishEvent(new PasswordEvent(PassEventType.RESET_REQUEST, email));
    }

    @Transactional
    public void resetUserPassword(PasswordResetRequestDTO dto) {
        Optional<PasswordResetToken> token = passwordResetService.findAndDeleteTokenByValue(dto.getResetToken());

        if (token.isEmpty() || token.get().isExpired()) {
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
                .path("/api/auth")
                .maxAge( logout ? Duration.ZERO : Duration.ofHours(refreshExpiryHours))
                .sameSite("Strict")
                .build();
    }

    public void invalidateAllUserSessions(String userAuthId) {
        if (userAuthId == null) { return; }
        tokenService.revokeTokensByUserAuthId(userAuthId);

    }

}
