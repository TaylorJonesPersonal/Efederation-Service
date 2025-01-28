package com.efederation.Service.impl;

import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.Model.Character;
import com.efederation.Model.Match;
import com.efederation.Model.NPC;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.MatchRepository;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.MatchService;
import com.efederation.Utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    NPCRepository npcRepository;

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Autowired
    CommonUtils commonUtils;

    @Transactional
    public void createMatch(Match newMatch) {
        matchRepository.save(newMatch);
    }

    public List<MatchAttributesResponse> getMatches (int wrestlerId) {
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Map<String, Object>> matches = matchRepository.getMatchesByWrestlerId(wrestlerId);
        List<MatchAttributesResponse> matchAttributeList = new ArrayList<>();
        matches.forEach(match -> {
            Map<String, Object> modifiableMap = new HashMap<>(match);
            Optional<NPC> optionalNPC = npcRepository.findById((Long) modifiableMap.get("npc_participants_npc_id"));
            optionalNPC.ifPresent(npc -> modifiableMap.put("npcName", npc.getAnnounceName()));
            Optional<Wrestler> optionalWrestler = wrestlerRepository.findById((long) wrestlerId);
            optionalWrestler.ifPresent(wrestler -> modifiableMap.put("wrestlerName", wrestler.getAnnounceName()));
            modifiableMap.remove("npc_participants_npc_id");
            LocalDateTime timestamp = commonUtils.convertTimestampWithoutExplicitT(modifiableMap.get("created_at").toString());
            String dateOnly = timestamp.format(formatter);
            modifiableMap.put("created_at", dateOnly);
            MatchAttributesResponse MatchAttributesResponse = objectMapper.convertValue(modifiableMap, MatchAttributesResponse.class);
            matchAttributeList.add(MatchAttributesResponse);
        });
        return matchAttributeList;
    }

    public Character defineWinner(List<Character> characters) {
        return characters.stream().max(Comparator.comparingInt(Character::fight)).orElse(characters.get(0));
    }
}
