package com.novaraspace.service;

import com.novaraspace.model.dto.user.UserSummary;
import com.novaraspace.model.entity.User;
import com.novaraspace.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Service
@RequestScope
public class CurrentUserService {

    private final UserRepository userRepository;
    private Optional<UserSummary> cachedCurrentUser = Optional.empty();
    private Long cachedId = null;
    private boolean alreadyFetched = false;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> getUserEntity() {
        if (alreadyFetched && cachedCurrentUser.isEmpty()) {
            return Optional.empty();
        }
        return loadAndCache();
    }

    public Optional<UserSummary> getUserSummary() {
        if (alreadyFetched) { return cachedCurrentUser; }

        Optional<User> user = loadAndCache();
        return user.map(this::entityToCurrentUser);
    }

    private Optional<User> loadAndCache() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || (auth instanceof  AnonymousAuthenticationToken)) {
            return Optional.empty();
        }

        Optional<User> user = Optional.empty();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            //This is only true during the login request.
            user = userRepository.findByEmail(auth.getName());
        } else {
            user = userRepository.findByAuthId(auth.getName());
        }
        alreadyFetched = true;
        user.ifPresent(u -> {
            cachedId = u.getId();
            cachedCurrentUser = Optional.of(entityToCurrentUser(u));
        });
        return user;
    }

    private UserSummary entityToCurrentUser(User user) {
        if (user == null) { return null; };
        return new UserSummary(
                user.getId(),
                user.getStatus(),
                user.isDemo(),
                user.getRoles(),
                user.getEmail()
        );
    }


}
