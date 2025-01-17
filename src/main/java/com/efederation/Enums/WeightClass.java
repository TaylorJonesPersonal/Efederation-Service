package com.efederation.Enums;

public enum WeightClass {
    CRUISERWEIGHT("Cruiserweight"),
    LIGHT_HEAVYWEIGHT("Light Heavyweight"),
    HEAVYWEIGHT("Heavyweight"),
    SUPER_HEAVYWEIGHT("Super Heavyweight");

    private final String displayName;

    WeightClass(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {return this.displayName;}
}
