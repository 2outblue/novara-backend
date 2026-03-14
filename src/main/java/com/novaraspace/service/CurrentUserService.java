package com.novaraspace.service;

import com.novaraspace.model.entity.User;
import com.novaraspace.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Service
@RequestScope
public class CurrentUserService {

    private final UserRepository userRepository;
    private Optional<User> cachedUser = Optional.empty();

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || (auth instanceof  AnonymousAuthenticationToken)) {
            return Optional.empty();
        }

        if (cachedUser.isPresent()) {
            return cachedUser;
        }

        //TODO: May need to check if auth instance is UsernamePassword... since getName will get the email if true
        String authId = auth.getName();
        cachedUser = userRepository.findByAuthId(authId);
        return cachedUser;
    }
}
