package com.novaraspace.model.enums.audit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum OutcomeFilter {
    ALL, SUCCESS, FAILURE;

    public Set<Outcome> toOutcomes() {
        List<Outcome> list = switch (this) {
            case ALL -> Arrays.asList(Outcome.values());
            case SUCCESS -> Arrays.asList(Outcome.SUCCESS);
            case FAILURE -> Arrays.asList(Outcome.FAILURE);
        };
        return new HashSet<Outcome>(list);
    }
}
