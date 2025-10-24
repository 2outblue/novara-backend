package com.novaraspace.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean eva;
    @Column(nullable = false)
    private boolean observationLounge;
    @Column(nullable = false)
    private boolean vr;
    @Column(nullable = false)
    private boolean galley;

    @JoinColumn(name = "first_class")
    @OneToOne
    private CabinClass firstClass;
    @JoinColumn(name = "middle_class")
    @OneToOne
    private CabinClass middleClass;
    @JoinColumn(name = "lower_class")
    @OneToOne
    private CabinClass lowerClass;

    public String getCode() {
        return code;
    }

    public Vehicle setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Vehicle setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEva() {
        return eva;
    }

    public Vehicle setEva(boolean eva) {
        this.eva = eva;
        return this;
    }

    public boolean isObservationLounge() {
        return observationLounge;
    }

    public Vehicle setObservationLounge(boolean observationLounge) {
        this.observationLounge = observationLounge;
        return this;
    }

    public boolean isVr() {
        return vr;
    }

    public Vehicle setVr(boolean vr) {
        this.vr = vr;
        return this;
    }

    public boolean isGalley() {
        return galley;
    }

    public Vehicle setGalley(boolean galley) {
        this.galley = galley;
        return this;
    }

    public CabinClass getFirstClass() {
        return firstClass;
    }

    public Vehicle setFirstClass(CabinClass firstClass) {
        this.firstClass = firstClass;
        return this;
    }

    public CabinClass getMiddleClass() {
        return middleClass;
    }

    public Vehicle setMiddleClass(CabinClass middleClass) {
        this.middleClass = middleClass;
        return this;
    }

    public CabinClass getLowerClass() {
        return lowerClass;
    }

    public Vehicle setLowerClass(CabinClass lowerClass) {
        this.lowerClass = lowerClass;
        return this;
    }
}
