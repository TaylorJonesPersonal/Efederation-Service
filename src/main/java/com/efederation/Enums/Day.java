package com.efederation.Enums;

public enum Day {

    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String displayName;

    Day(String displayName) {
        this.displayName = displayName;
    }

    public String toString() {
        return this.displayName;
    }
}
