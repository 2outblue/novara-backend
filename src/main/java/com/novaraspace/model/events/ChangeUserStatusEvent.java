package com.novaraspace.model.events;

import com.novaraspace.model.enums.AccountStatus;

public record ChangeUserStatusEvent(
        Long targetUserId,
        String targetUserEmail,
        AccountStatus newStatus
) { }
