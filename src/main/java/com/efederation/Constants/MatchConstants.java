package com.efederation.Constants;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Data
public class MatchConstants {
    private final String[] moves = {
            "Snap Suplex", "Snapmare", "Corner Running Splash", "Dropkick", "Running Lariat",
    "Powerbomb", "Scoop Slam", "Running Knee", "DDT", "Back Body Drop"
    };

    private final String DEVASTATION = "Devastation|%WINNER performed a %MOVE. %WINNER then performed a %MOVE on %LOSER. After a long battle, %WINNER wins by %VICTORY_CONDITION.";
    private final String LADDER_ATTACK = "Ladder Attack|%WINNER hit %LOSER with a ladder!";
    private final String STEEL_CHAIR_MISFIRE = "Steel Chair Misfire|%LOSER tried to hit %WINNER with a chair, but missed!";

    // make a custom class here with private var String[] for each %s and what it represents. This can be parsed and
    // winners, losers and etc. can be understood to be passed here.
    private final List<String> matchEventArr = List.of(DEVASTATION, LADDER_ATTACK);

    public Set<String> drawEvents() {
        Set<String> drawnEvents = new HashSet<>();
        Random random = new Random();
        int numberOfEvents = random.nextInt(5);
        for(int i = 0; i < numberOfEvents; i++) {
            int roll = random.nextInt(matchEventArr.size());
            for(String event : matchEventArr) {
                if(matchEventArr.indexOf(event) == roll) {
                    drawnEvents.add(event);
                }
            }
        }
        return drawnEvents;
    }
}
