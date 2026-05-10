package com.novaraspace.model.enums;

import java.time.LocalDate;

public enum PaxAgeGroup {
    junior(12, 18), adult(18, 120);

    private final int minAge;
    private final int maxAge;

    PaxAgeGroup(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public boolean validAge(LocalDate dob) {
        LocalDate maxDate = LocalDate.now().minusYears(minAge).plusDays(5);
        LocalDate minDate = LocalDate.now().minusYears(maxAge).minusDays(5);
        return dob.isAfter(minDate) && dob.isBefore(maxDate);
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
