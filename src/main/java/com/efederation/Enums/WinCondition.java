package com.efederation.Enums;

public enum WinCondition {
    PINFALL("Pinfall"),
    SUBMISSION("Submission"),
    DISQUALIFICATION("Disqualification"),
    COUNT_OUT("Count out"),
    KNOCK_OUT("Knock out"),
    QUIT("Quit"),
    SERIOUS_INJURY("Serious Injury");

    private final String displayName;

    WinCondition(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
