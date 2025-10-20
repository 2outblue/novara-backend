package com.novaraspace.config;

import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.ErrCode;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getEntityByEmail(email).orElseThrow(() -> new BadCredentialsException("Bad credentials."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials.");
        }

        AccountStatus status = user.getStatus();
        switch (status) {
            case PENDING_ACTIVATION -> throw new UserException(ErrCode.USER_NOT_ACTIVATED, HttpStatus.FORBIDDEN, "User not activated.");
            case SUSPENDED, DEACTIVATED -> throw new UserException(ErrCode.USER_DISABLED, HttpStatus.FORBIDDEN, "User disabled.");
        }

        if (user.getStatus().equals(AccountStatus.ACTIVE)) {
            userService.setLastLoginNow(user.getEmail());
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .toList();
            return new UsernamePasswordAuthenticationToken(email, password, authorities);
        }

        throw new BadCredentialsException("Bad credentials.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
