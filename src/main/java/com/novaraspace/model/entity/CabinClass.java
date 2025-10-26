package com.novaraspace.model.entity;

import com.novaraspace.model.enums.CabinClassEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "cabin_class")
public class CabinClass {
    @Id
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CabinClassEnum type;
    @Column(nullable = false)
    private int totalSeats;
    @Column(nullable = false)
    private boolean windowAvailable;

    public Long getId() {
        return id;
    }

    public CabinClass setId(Long id) {
        this.id = id;
        return this;
    }

    public CabinClassEnum getType() {
        return type;
    }

    public CabinClass setType(CabinClassEnum type) {
        this.type = type;
        return this;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public CabinClass setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
        return this;
    }

    public boolean isWindowAvailable() {
        return windowAvailable;
    }

    public CabinClass setWindowAvailable(boolean window) {
        this.windowAvailable = window;
        return this;
    }
}
