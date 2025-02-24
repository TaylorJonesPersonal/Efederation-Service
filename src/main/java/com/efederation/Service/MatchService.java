package com.efederation.Service;

import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.DTO.WinnersLosers;
import com.efederation.Exception.MatchCreationException;
import com.efederation.Model.Character;

import java.util.List;

public interface MatchService {

    List<MatchAttributesResponse> getMatches(int wrestlerId) throws MatchCreationException;
    CreateMatchResponse createMatch(CreateMatchRequest matchRequest) throws MatchCreationException;

    WinnersLosers defineWinnersLosers(List<Character> characters);

    String defineCondition();
}
