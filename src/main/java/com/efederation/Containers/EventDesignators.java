package com.efederation.Containers;

import lombok.Builder;
import lombok.Data;

@Data
public class EventDesignators {
    private String winner;
    private String loser;
    private String referee;
    private String opponent;
    private String playerCharacter;
    private String victoryCondition;

    @Builder
    public EventDesignators(String winner, String loser, String referee, String opponent, String playerCharacter, String victoryCondition) {
        this.winner = winner;
        this.loser = loser;
        this.referee = referee;
        this.opponent = opponent;
        this.playerCharacter = playerCharacter;
        this.victoryCondition = victoryCondition;
    }
}
