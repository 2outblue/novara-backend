package com.novaraspace.model.events;

import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;

public record ScheduledTaskEvent(
        AuditScheduledTaskType type,
        int count,
        Outcome outcome
) {
}
