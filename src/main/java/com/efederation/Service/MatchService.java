package com.efederation.Service;

import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.DTO.WinnersLosers;
import com.efederation.Model.Character;

import java.util.List;

public interface MatchService {

    List<MatchAttributesResponse> getMatches(int wrestlerId);
    CreateMatchResponse createMatch(CreateMatchRequest matchRequest);

    WinnersLosers defineWinnersLosers(List<Character> characters);

    String defineCondition();
}
