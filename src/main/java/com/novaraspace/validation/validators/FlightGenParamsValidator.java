package com.novaraspace.validation.validators;

import com.novaraspace.model.dto.flight.FlightInstanceGenerationParams;
import com.novaraspace.validation.annotations.ValidFlightGenParams;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class FlightGenParamsValidator implements ConstraintValidator<ValidFlightGenParams, FlightInstanceGenerationParams> {

    @Value("${app.flight.max-future-departure-days}")
    private int maxFutureDepartureDays;

    @Override
    public boolean isValid(FlightInstanceGenerationParams value, ConstraintValidatorContext context) {
        if (value == null || value.getStartDate() == null || value.getEndDate() == null) {
            return false;
        }

        if (value.getStartDate().isBefore(value.getEndDate())) { return false; }

        LocalDate now = LocalDate.now();
        return value.getEndDate().isBefore(now.plusDays(maxFutureDepartureDays + 1));
    }
}
