package com.novaraspace.init.data;

import java.time.Instant;
import java.time.LocalDateTime;

public class TestingDTO {
    private Long id;
    private LocalDateTime date;
    private Instant instant;

    public Long getId() {
        return id;
    }

    public TestingDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TestingDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Instant getInstant() {
        return instant;
    }

    public TestingDTO setInstant(Instant instant) {
        this.instant = instant;
        return this;
    }
}
