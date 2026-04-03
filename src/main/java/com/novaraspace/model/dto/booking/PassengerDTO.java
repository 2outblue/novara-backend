package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidPassengerAgeGroup;
import com.novaraspace.validation.annotations.ValidPassengerTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class PassengerDTO {
    @NotNull
    private Long id; //intraBookingId
    @NotBlank
    @Size(min = 1, max = 20)
    @ValidPassengerTitle
    private String title;
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotNull
    @Past
    private LocalDate dob;
    @NotBlank
    @Size(min = 1, max = 20)
    @ValidPassengerAgeGroup
    private String ageGroup;
    @NotNull
    private Long cabinId;
    @NotNull
    @Valid
    private PassengerBaggageDTO baggage;

    public Long getId() {
        return id;
    }

    public PassengerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PassengerDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PassengerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PassengerDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public PassengerDTO setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public PassengerDTO setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public Long getCabinId() {
        return cabinId;
    }

    public PassengerDTO setCabinId(Long cabinId) {
        this.cabinId = cabinId;
        return this;
    }

    public PassengerBaggageDTO getBaggage() {
        return baggage;
    }

    public PassengerDTO setBaggage(PassengerBaggageDTO baggage) {
        this.baggage = baggage;
        return this;
    }
}
