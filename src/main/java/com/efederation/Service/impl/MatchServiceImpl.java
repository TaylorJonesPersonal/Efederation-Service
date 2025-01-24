package com.efederation.Service.impl;

import com.efederation.DTO.MatchAttributes;
import com.efederation.DTO.MatchResponse;
import com.efederation.Model.Character;
import com.efederation.Model.Match;
import com.efederation.Model.NPC;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.MatchRepository;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.MatchService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    NPCRepository npcRepository;

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Transactional
    public void createMatch(Match newMatch) {
        matchRepository.save(newMatch);
    }

    public MatchResponse getMatches (int wrestlerId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        List<Map<String, Object>> matches = matchRepository.getMatchesByWrestlerId(wrestlerId);
        List<MatchAttributes> matchAttributeList = new ArrayList<>();
        matches.forEach(match -> {
            Map<String, Object> modifiableMap = new HashMap<>(match);
            Optional<NPC> optionalNPC = npcRepository.findById((Long) modifiableMap.get("npc_participants_npc_id"));
            optionalNPC.ifPresent(npc -> modifiableMap.put("npcName", npc.getAnnounceName()));
            Optional<Wrestler> optionalWrestler = wrestlerRepository.findById((long) wrestlerId);
            optionalWrestler.ifPresent(wrestler -> modifiableMap.put("wrestlerName", wrestler.getAnnounceName()));
            modifiableMap.remove("npc_participants_npc_id");
            MatchAttributes matchAttributes = objectMapper.convertValue(modifiableMap, MatchAttributes.class);
            matchAttributeList.add(matchAttributes);
        });
        return MatchResponse.builder().wrestlerId(wrestlerId).matches(matchAttributeList).build();
    }

    public Character defineWinner(List<Character> characters) {
        return characters.stream().max(Comparator.comparingInt(Character::fight)).orElse(characters.get(0));
    }
}
