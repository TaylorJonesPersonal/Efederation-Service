package com.efederation.Enums;

public enum Targets {

    PLAYER_CHARACTER("%PLAYER_CHARACTER"),
    AUDIENCE("the Audience"),
    MOVE("%MOVE"),
    OPPONENT("%OPPONENT"),
    REF("The referee, %REFEREE"),
    WINNER("%WINNER"),
    LOSER("%LOSER"),
    VICTORY_CONDITION("%VICTORY_CONDITION");

    private final String displayName;

    Targets(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {return this.displayName;}
}
