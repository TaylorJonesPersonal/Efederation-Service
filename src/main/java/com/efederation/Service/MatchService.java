package com.efederation.Service;

import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.Model.Character;
import com.efederation.Model.Match;

import java.util.List;

public interface MatchService {

    List<MatchAttributesResponse> getMatches(int wrestlerId);
    void createMatch(Match newMatch);

    Character defineWinner(List<Character> characters);

    String defineCondition();
}
