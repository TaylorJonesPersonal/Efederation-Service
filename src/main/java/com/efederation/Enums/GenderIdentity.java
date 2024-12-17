package com.efederation.Enums;

public enum GenderIdentity {
    MALE("Male"),
    FEMALE("Female"),
    NONBINARY("Non-Binary");

    private final String displayName;

    GenderIdentity(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
