package com.efederation.Service.impl;

import com.efederation.Constants.CommonConstants;
import com.efederation.Constants.MatchConstants;
import com.efederation.DTO.*;
import com.efederation.Enums.Targets;
import com.efederation.Enums.WinCondition;
import com.efederation.Model.*;
import com.efederation.Model.Character;
import com.efederation.Repository.MatchEventRepository;
import com.efederation.Repository.MatchRepository;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.MatchService;
import com.efederation.Utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    MatchConstants matchConstants;

    @Autowired
    ObjectMapper objectMapper;

    @Transactional
    public CreateMatchResponse createMatch(CreateMatchRequest createMatchRequest) {
        Set<Wrestler> wrestlerSet = new HashSet<>();
        Set<NPC> npcSet = new HashSet<>();
        for(String participant_id : createMatchRequest.getParticipant_ids()) {
            if(participant_id.startsWith(CommonConstants.npcDenotation)) {
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
        Set<String> drawnEvents = matchConstants.drawEvents();
        Set<MatchEvent> matchEvents = drawnEvents
                .stream()
                .map(event -> formatEventDescription(
                        event,
                        EventDesignators
                                .builder()
                                .winner(winnersLosers.getWinner().getAnnounceName())
                                .loser(winnersLosers.getLoser().getAnnounceName())
                                .victoryCondition(victoryCondition).build()))
                .collect(Collectors.toSet());
        Match newMatch = Match.builder()
                .human_participants(wrestlerSet)
                .npc_participants(npcSet)
                .winner(winnersLosers.getWinner().getAnnounceName())
                .condition(victoryCondition)
                .matchEvents(matchEvents)
                .build();
        newMatch.getMatchEvents().forEach(event -> event.setMatch(newMatch));
        matchRepository.save(newMatch);
        return CreateMatchResponse.builder().winnerName(winnersLosers.getWinner().getAnnounceName()).build();
    }

    public MatchEvent formatEventDescription(String drawnEvent, EventDesignators eventDesignators) {
        Random random = new Random();
        Pattern pattern = Pattern.compile(Pattern.quote(Targets.MOVE.toString()));
        String title = drawnEvent.split(CommonConstants.PIPE_REG_EX)[0].replaceAll(CommonConstants.PIPE_REG_EX, CommonConstants.BLANK);
        Matcher matcher = pattern.matcher(drawnEvent);
        StringBuffer sb = new StringBuffer();
        String priorMoveReplacement = CommonConstants.BLANK;
        while(matcher.find()) {
            String replacedMove = matchConstants.getMoves()[random.nextInt(matchConstants.getMoves().length)];
            if(replacedMove.equals(priorMoveReplacement)) {
                matcher.appendReplacement(sb, CommonConstants.ANOTHER + " " + replacedMove);
            } else {
                matcher.appendReplacement(sb, CommonConstants.A + " " + replacedMove);
            }
            priorMoveReplacement = replacedMove;
        }
        matcher.appendTail(sb);
        String formattedEvent = sb.toString()
                .split(CommonConstants.PIPE_REG_EX)[1]
                .replaceAll(Targets.LOSER.toString(), eventDesignators.loser)
                .replaceAll(Targets.WINNER.toString(), eventDesignators.winner)
                .replaceAll(Targets.VICTORY_CONDITION.toString(), eventDesignators.victoryCondition);
        return MatchEvent.builder().name(title).description(formattedEvent).build();
    }

    public List<MatchAttributesResponse> getMatches (int wrestlerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.STD_DATE_FORMAT);
        List<Map<String, Object>> matches = matchRepository.getMatchesByWrestlerId(wrestlerId);
        List<MatchAttributesResponse> matchAttributeList = new ArrayList<>();
        matches.forEach(match -> {
            Map<String, Object> modifiableMap = new HashMap<>(match);
            Optional<NPC> optionalNPC = npcRepository.findById((Long) modifiableMap.get("npc_participants_npc_id"));
            Optional<Wrestler> optionalWrestler = wrestlerRepository.findById((long) wrestlerId);
            optionalWrestler.ifPresent(wrestler -> optionalNPC.ifPresent(npc -> {
                    modifiableMap.put("participants", String.format(CommonConstants.PARTICIPANT_VS, wrestler.getAnnounceName(), npc.getAnnounceName()));
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
            List<MatchAttributesResponseEvent> matchAttributesEventList = new ArrayList<>();
            matchEvents.forEach(matchEvent -> {
                MatchAttributesResponseEvent event = MatchAttributesResponseEvent.builder().name(matchEvent.getName()).description(matchEvent.getDescription()).build();
                matchAttributesEventList.add(event);
            });
            attributes.setEvents(matchAttributesEventList);
            MatchAttributesResponse matchAttributesResponse = new MatchAttributesResponse(Long.parseLong(match.get("matchId").toString()), attributes);
            matchAttributeList.add(matchAttributesResponse);
        });
        return matchAttributeList;
    }

    @Data
    public static class EventDesignators {
        private String winner;
        private String loser;
        private String victoryCondition;

        @Builder
        public EventDesignators(String winner, String loser, String victoryCondition) {
            this.winner = winner;
            this.loser = loser;
            this.victoryCondition = victoryCondition;
        }
    }

    public WinnersLosers defineWinnersLosers(List<Character> characters) {
        Comparator<Character> characterComparator = Comparator.comparingInt(Character::fight);
        Character winner = characters.stream().max(characterComparator).orElse(characters.get(0));
        Character loser = characters.stream().filter(character -> !character.equals(winner)).min(characterComparator).orElse(characters.get(1));
        return WinnersLosers
                .builder()
                .winner(winner)
                .loser(loser)
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
