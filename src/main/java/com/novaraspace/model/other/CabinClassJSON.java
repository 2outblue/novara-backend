package com.novaraspace.model.other;

public class CabinClassJSON {

    private long id;
    private int seats;
    private boolean windowAvailable;
    private String classType;

    public long getId() {
        return id;
    }

    public CabinClassJSON setId(long id) {
        this.id = id;
        return this;
    }

    public int getSeats() {
        return seats;
    }

    public CabinClassJSON setSeats(int seats) {
        this.seats = seats;
        return this;
    }

    public boolean isWindowAvailable() {
        return windowAvailable;
    }

    public CabinClassJSON setWindowAvailable(boolean windowAvailable) {
        this.windowAvailable = windowAvailable;
        return this;
    }

    public String getClassType() {
        return classType;
    }

    public CabinClassJSON setClassType(String classType) {
        this.classType = classType;
        return this;
    }
}
