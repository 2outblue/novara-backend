package com.novaraspace.security;

import com.novaraspace.model.dto.user.UserSummary;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.service.CurrentUserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DemoAccessGuard {

    private final CurrentUserService currentUserService;

    public DemoAccessGuard(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public boolean canModify() {
        Optional<UserSummary> userSummary = currentUserService.getUserSummary();
        if (userSummary.isEmpty()) {
            throw UserException.notFound();
        }

        if (userSummary.get().isDemo()) {
            throw UserException.demo();
        }
        return true;
    }
}
