package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidPassengerAgeGroup;
import com.novaraspace.validation.annotations.ValidPassengerTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class NewPassengerDTO {
    @NotNull
    private Long id;
    @NotEmpty
    @ValidPassengerTitle
    private String title;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastName;
    @NotNull
    @Past
    private LocalDate dob;
    @NotEmpty
    @ValidPassengerAgeGroup
    private String ageGroup;
    @NotNull
    private Long cabinId;
    @NotNull
    @Valid
    private NewPassengerBaggageDTO baggage;

    public Long getId() {
        return id;
    }

    public NewPassengerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewPassengerDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public NewPassengerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public NewPassengerDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public NewPassengerDTO setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public NewPassengerDTO setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public Long getCabinId() {
        return cabinId;
    }

    public NewPassengerDTO setCabinId(Long cabinId) {
        this.cabinId = cabinId;
        return this;
    }

    public NewPassengerBaggageDTO getBaggage() {
        return baggage;
    }

    public NewPassengerDTO setBaggage(NewPassengerBaggageDTO baggage) {
        this.baggage = baggage;
        return this;
    }
}
