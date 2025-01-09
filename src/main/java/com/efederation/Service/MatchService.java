package com.efederation.Service;

import com.efederation.Model.Character;
import com.efederation.Model.Match;

import java.util.List;

public interface MatchService {
    void createMatch(Match newMatch);

    Character defineWinner(List<Character> characters);
}
