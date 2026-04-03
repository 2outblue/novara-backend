package com.novaraspace.model.dto.user;

import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;

import java.util.Set;

public record UserSummary(
        Long id,
        AccountStatus status,
        boolean isDemo,
        Set<UserRole> roles,
        String email
) {
}
