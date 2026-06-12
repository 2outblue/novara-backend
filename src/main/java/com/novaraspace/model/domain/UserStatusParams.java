package com.novaraspace.model.domain;

import com.novaraspace.model.enums.AccountStatus;

import java.time.Instant;

public record UserStatusParams(
        Instant date,
        AccountStatus status
) {
}
