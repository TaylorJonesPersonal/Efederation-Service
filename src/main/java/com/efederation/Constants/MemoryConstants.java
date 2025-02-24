package com.efederation.Constants;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Data
public class MemoryConstants {

    private final String TAUNT_HUMILIATION = "Taunt Humiliation|%OPPONENT ridiculed %PLAYER_CHARACTER with their own" +
            " taunt. It's clear %PLAYER_CHARACTER won't be able to use that again. The audience now associates that taunt" +
            " with %OPPONENT.";

    private final String REFEREE_BIASED = "Referee Biased|%OPPONENT brought a weapon in to the ring right in front of the" +
            " referee, %REFEREE. As they went to swing, it looked like the ref wasn't going to stop it. %PLAYER_CHARACTER avoided the" +
            " weapon strike, but this bias clearly will favor %OPPONENT";


    private final List<String> matchMemoryArr = List.of(TAUNT_HUMILIATION, REFEREE_BIASED);

    public Set<String> drawMemories() {
        Set<String> drawnEvents = new HashSet<>();
        Random random = new Random();
        int numberOfEvents = random.nextInt(5);
        for(int i = 0; i < numberOfEvents; i++) {
            int roll = random.nextInt(matchMemoryArr.size());
            for(String event : matchMemoryArr) {
                if(matchMemoryArr.indexOf(event) == roll) {
                    drawnEvents.add(event);
                }
            }
        }
        return drawnEvents;
    }
}
