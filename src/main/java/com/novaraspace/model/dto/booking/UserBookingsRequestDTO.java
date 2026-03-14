package com.novaraspace.model.dto.booking;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

public class UserBookingsRequestDTO {
    @Max(100)
    @PositiveOrZero
    private int page;
    @Range(min = 1, max = 30)
    private int size;
    @NotBlank
    @Pattern(regexp = "^(upcoming|history)$", message = "Type must be either 'upcoming' or 'history'")
    private String timeFrame;

    public int getPage() {
        return page;
    }

    public UserBookingsRequestDTO setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public UserBookingsRequestDTO setSize(int size) {
        this.size = size;
        return this;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public UserBookingsRequestDTO setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }
}
