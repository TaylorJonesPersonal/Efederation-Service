package com.efederation.Service.impl;

import com.efederation.Constants.CommonConstants;
import com.efederation.Constants.MatchConstants;
import com.efederation.DTO.*;
import com.efederation.Enums.WinCondition;
import com.efederation.Model.*;
import com.efederation.Model.Character;
import com.efederation.Repository.MatchEventRepository;
import com.efederation.Repository.MatchRepository;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.MatchService;
import com.efederation.Utils.CommonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    MatchEventRepository matchEventRepository;

    @Autowired
    NPCRepository npcRepository;

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    CommonConstants commonConstants;

    @Autowired
    MatchConstants matchConstants;

    @Transactional
    public CreateMatchResponse createMatch(CreateMatchRequest createMatchRequest) {
        Random random = new Random();
        Set<Wrestler> wrestlerSet = new HashSet<>();
        Set<NPC> npcSet = new HashSet<>();
        for(String participant_id : createMatchRequest.getParticipant_ids()) {
            if(participant_id.startsWith(commonConstants.getNpcDenotation())) {
                Optional<NPC> npcOptional = npcRepository.findById(Long.parseLong(participant_id.substring(1)));
                npcOptional.ifPresent(npcSet::add);
            } else {
                Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(Long.parseLong(participant_id));
                wrestlerOptional.ifPresent(wrestlerSet::add);
            }
        }
        List<Character> combinedArray = new ArrayList<>(wrestlerSet);
        combinedArray.addAll(npcSet);
        WinnersLosers winnersLosers = defineWinnersLosers(combinedArray);
        String victoryCondition = defineCondition();
        String eventDescription = "%s performed a %s. %s then performed a %s on %s. After a long battle, %s wins by %s.";
        MatchEvent matchEvent = MatchEvent.builder().name("Devastation").description(
                String.format(
                        eventDescription,
                        winnersLosers.getWinner().getAnnounceName(),
                        matchConstants.getMoves()[random.nextInt(matchConstants.getMoves().length)],
                        winnersLosers.getWinner().getAnnounceName(),
                        matchConstants.getMoves()[random.nextInt(matchConstants.getMoves().length)],
                        winnersLosers.getLoser().getAnnounceName(),
                        winnersLosers.getWinner().getAnnounceName(),
                        victoryCondition
                )).build();
        Match newMatch = Match.builder()
                .human_participants(wrestlerSet)
                .npc_participants(npcSet)
                .winner(winnersLosers.getWinner().getAnnounceName())
                .condition(victoryCondition)
                .matchEvents(Set.of(matchEvent))
                .build();
        newMatch.getMatchEvents().forEach(event -> event.setMatch(newMatch));
        matchRepository.save(newMatch);
        return CreateMatchResponse.builder().winnerName(winnersLosers.getWinner().getAnnounceName()).build();
    }

    public List<MatchAttributesResponse> getMatches (int wrestlerId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Map<String, Object>> matches = matchRepository.getMatchesByWrestlerId(wrestlerId);
        List<MatchAttributesResponse> matchAttributeList = new ArrayList<>();
        matches.forEach(match -> {
            Map<String, Object> modifiableMap = new HashMap<>(match);
            Optional<NPC> optionalNPC = npcRepository.findById((Long) modifiableMap.get("npc_participants_npc_id"));
            Optional<Wrestler> optionalWrestler = wrestlerRepository.findById((long) wrestlerId);
            String participants = "%s vs. %s";
            optionalWrestler.ifPresent(wrestler -> optionalNPC.ifPresent(npc -> {
                    modifiableMap.put("participants", String.format(participants, wrestler.getAnnounceName(), npc.getAnnounceName()));
                    if(Objects.equals(npc.getAnnounceName(), modifiableMap.get("winner").toString())) {
                        modifiableMap.put("defeatedImage", wrestler.getDefeatedImage());
                    } else {
                        modifiableMap.put("defeatedImage", npc.getDefeatedImage());
                    }
                }
            ));
            LocalDateTime timestamp = commonUtils.convertTimestampWithoutExplicitT(modifiableMap.get("created_at").toString());
            String dateOnly = timestamp.format(formatter);
            modifiableMap.put("created_at", dateOnly);
            List<MatchEvent> matchEvents = matchEventRepository.findAllByMatchMatchId((Long) modifiableMap.get("match_id"));
            MatchAttributes attributes = objectMapper.convertValue(modifiableMap, MatchAttributes.class);
            List<MatchAttributesEvent> matchAttributesEventList = new ArrayList<>();
            matchEvents.forEach(matchEvent -> {
                MatchAttributesEvent event = MatchAttributesEvent.builder().name(matchEvent.getName()).description(matchEvent.getDescription()).build();
                matchAttributesEventList.add(event);
            });
            attributes.setEvents(matchAttributesEventList);
            MatchAttributesResponse matchAttributesResponse = new MatchAttributesResponse(Long.parseLong(match.get("matchId").toString()), attributes);
            matchAttributeList.add(matchAttributesResponse);
        });
        return matchAttributeList;
    }

    public WinnersLosers defineWinnersLosers(List<Character> characters) {
        return WinnersLosers
                .builder()
                .winner(characters.stream().max(Comparator.comparingInt(Character::fight)).orElse(characters.get(0)))
                .loser(characters.stream().min(Comparator.comparingInt(Character::fight)).orElse(characters.get(characters.size() - 1)))
                .build();
    }

    public String defineCondition() {
        Random random = new Random();
        int roll = random.nextInt(10);
        return switch (roll) {
            case 4, 5 -> WinCondition.SUBMISSION.toString();
            case 6 -> WinCondition.DISQUALIFICATION.toString();
            case 7 -> WinCondition.COUNT_OUT.toString();
            default -> WinCondition.PINFALL.toString();
        };
    }
}
