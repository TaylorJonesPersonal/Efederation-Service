package com.efederation.Enums;

public enum Importance {
    MAJOR("Major"),
    MINOR("Minor");

    private final String displayName;

    Importance(String displayName) {this.displayName = displayName;}

    @Override
    public String toString() {
        return this.displayName;
    }
}
