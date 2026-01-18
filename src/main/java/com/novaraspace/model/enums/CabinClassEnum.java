package com.novaraspace.model.enums;

public enum CabinClassEnum {
    FIRST("A Class"),
    MIDDLE("B Class"),
    LOWER("C Class");

    private final String displayName;

    CabinClassEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
