package com.efederation.Service.impl;

import com.efederation.Constants.CommonConstants;
import com.efederation.Constants.MatchConstants;
import com.efederation.Constants.MemoryConstants;
import com.efederation.DTO.*;
import com.efederation.Enums.Targets;
import com.efederation.Enums.WinCondition;
import com.efederation.Exception.MatchCreationException;
import com.efederation.Model.*;
import com.efederation.Model.Character;
import com.efederation.Repository.*;
import com.efederation.Service.MatchService;
import com.efederation.Utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.efederation.Containers.EventDesignators;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
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
    MemoryRepository memoryRepository;

    @Autowired
    NPCRepository npcRepository;

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    MatchConstants matchConstants;

    @Autowired
    MemoryConstants memoryConstants;

    @Autowired
    ObjectMapper objectMapper;

    @Transactional
    public CreateMatchResponse createMatch(CreateMatchRequest createMatchRequest) throws MatchCreationException {
        try {
            Set<Wrestler> wrestlerSet = new HashSet<>();
            Set<NPC> npcSet = new HashSet<>();
            for (String participant_id : createMatchRequest.getParticipant_ids()) {
                if (participant_id.startsWith(CommonConstants.npcDenotation)) {
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
            EventDesignators eventDesignators = EventDesignators.builder()
                    .winner(winnersLosers.getWinner().getAnnounceName())
                    .loser(winnersLosers.getLoser().getAnnounceName())
                    .playerCharacter(wrestlerSet.stream().findFirst().orElseThrow().getAnnounceName())
                    .opponent(npcSet.stream().findFirst().orElseThrow().getAnnounceName())
                    .referee("Bart McHammond")
                    .victoryCondition(victoryCondition).build();
            Set<MatchEvent> matchEvents = drawnEvents
                    .stream()
                    .map(event ->
                            {
                                try {
                                    return (MatchEvent) formatDescription(
                                            event,
                                            eventDesignators,
                                            MatchEvent.class);
                                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                                         IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
                    .collect(Collectors.toSet());
            Set<String> drawnMemories = memoryConstants.drawMemories();
            Set<Memory> matchMemories = drawnMemories
                    .stream()
                    .map(event -> {
                        try {
                            return (Memory) formatDescription(
                                    event,
                                    eventDesignators,
                                    Memory.class
                            );
                        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException |
                                 IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            Match newMatch = Match.builder()
                    .human_participants(wrestlerSet)
                    .npc_participants(npcSet)
                    .winner(winnersLosers.getWinner().getAnnounceName())
                    .condition(victoryCondition)
                    .matchEvents(matchEvents)
                    .matchMemories(matchMemories)
                    .build();
            newMatch.getMatchEvents().forEach(event -> event.setMatch(newMatch));
            newMatch.getMatchMemories().forEach(memory -> memory.setMatch(newMatch));
            matchRepository.save(newMatch);
            return CreateMatchResponse.builder().winnerName(winnersLosers.getWinner().getAnnounceName()).build();
        } catch(RuntimeException e) {
            e.printStackTrace();
            throw new MatchCreationException(e.getMessage());
        }
    }

    public Event formatDescription(String drawn, EventDesignators eventDesignators, Class<? extends Event> designatedClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Random random = new Random();
        Pattern pattern = Pattern.compile(Pattern.quote(Targets.MOVE.toString()));
        String title = drawn.split(CommonConstants.PIPE_REG_EX)[0].replaceAll(CommonConstants.PIPE_REG_EX, CommonConstants.BLANK);
        Matcher matcher = pattern.matcher(drawn);
        StringBuilder sb = new StringBuilder();
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
                .replaceAll(Targets.LOSER.toString(), eventDesignators.getLoser())
                .replaceAll(Targets.WINNER.toString(), eventDesignators.getWinner())
                .replaceAll(Targets.REF.toString(), eventDesignators.getReferee())
                .replaceAll(Targets.OPPONENT.toString(), eventDesignators.getOpponent())
                .replaceAll(Targets.PLAYER_CHARACTER.toString(), eventDesignators.getPlayerCharacter())
                .replaceAll(Targets.VICTORY_CONDITION.toString(), eventDesignators.getVictoryCondition());
        Event newMatchEvent = designatedClass.getDeclaredConstructor().newInstance();
        newMatchEvent.setDescription(formattedEvent);
        newMatchEvent.setName(title);
        return newMatchEvent;
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
                        modifiableMap.put("defeatedImage", wrestler.getImageSet().getDefeatedImage());
                    } else {
                        modifiableMap.put("defeatedImage", npc.getImageSet().getDefeatedImage());
                    }
                }
            ));
            LocalDateTime timestamp = commonUtils.convertTimestampWithoutExplicitT(modifiableMap.get("created_at").toString());
            String dateOnly = timestamp.format(formatter);
            modifiableMap.put("created_at", dateOnly);
            List<MatchEvent> matchEvents = matchEventRepository.findAllByMatchMatchId((Long) modifiableMap.get("match_id"));
            List<Memory> matchMemories = memoryRepository.findAllByMatchMatchId((Long) modifiableMap.get("match_id"));
            MatchAttributes attributes = objectMapper.convertValue(modifiableMap, MatchAttributes.class);
            List<MatchAttributesResponseEvent> matchAttributesEventList = new ArrayList<>();
            List<MatchAttributesResponseEvent> matchAttributesMemoriesList = new ArrayList<>();
            matchEvents.forEach(matchEvent -> {
                MatchAttributesResponseEvent event = MatchAttributesResponseEvent.builder().name(matchEvent.getName()).description(matchEvent.getDescription()).build();
                matchAttributesEventList.add(event);
            });
            matchMemories.forEach(matchMemory -> {
                MatchAttributesResponseEvent memory = MatchAttributesResponseEvent.builder().name(matchMemory.getName()).description(matchMemory.getDescription()).build();
                matchAttributesMemoriesList.add(memory);
            });
            attributes.setEvents(matchAttributesEventList);
            attributes.setMemories(matchAttributesMemoriesList);
            MatchAttributesResponse matchAttributesResponse = new MatchAttributesResponse(Long.parseLong(match.get("matchId").toString()), attributes);
            matchAttributeList.add(matchAttributesResponse);
        });
        return matchAttributeList;
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
