package com.novaraspace.service;

import com.novaraspace.model.entity.User;
import com.novaraspace.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private boolean alreadyFetched = false;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || (auth instanceof  AnonymousAuthenticationToken)) {
            return Optional.empty();
        }

        if (alreadyFetched) {
            return cachedUser;
        }

        if (auth instanceof UsernamePasswordAuthenticationToken) {
            //This is only true during the login request.
            cachedUser = userRepository.findByEmail(auth.getName());
        } else {
            cachedUser = userRepository.findByAuthId(auth.getName());
        }
        alreadyFetched = true;
        return cachedUser;
    }
}
