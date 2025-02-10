package com.efederation.Controller;

import com.efederation.Constants.CommonConstants;
import com.efederation.Constants.MatchConstants;
import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.Model.*;
import com.efederation.Model.Character;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Autowired
    NPCRepository npcRepository;

    @Autowired
    MatchService matchService;

    @Autowired
    CommonConstants commonConstants;

    @Autowired
    MatchConstants matchConstants;

    @GetMapping("/{wrestlerId}")
    public ResponseEntity<List<MatchAttributesResponse>> getMatches(@PathVariable int wrestlerId) {
        List<MatchAttributesResponse> matchResponse = matchService.getMatches(wrestlerId);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }


    //refactor - move all of this to a service. Should not be in a controller.
    @PostMapping("/create")
    public ResponseEntity<CreateMatchResponse> createMatch(@RequestBody CreateMatchRequest matchRequest) {
        Random random = new Random();
        Set<Wrestler> wrestlerSet = new HashSet<>();
        Set<NPC> npcSet = new HashSet<>();
        for(String participant_id : matchRequest.getParticipant_ids()) {
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
        Character winner = matchService.defineWinner(combinedArray);
        String eventDescription = "%s performed a %s. %s then performed a %s on %s";
        MatchEvent matchEvent = MatchEvent.builder().name("Devastation").description(
                String.format(
                        eventDescription,
                        Objects.requireNonNull(wrestlerSet.stream().findFirst().orElse(null)).getAnnounceName(),
                        matchConstants.getMoves()[random.nextInt(matchConstants.getMoves().length)],
                        Objects.requireNonNull(wrestlerSet.stream().findFirst().orElse(null)).getAnnounceName(),
                        matchConstants.getMoves()[random.nextInt(matchConstants.getMoves().length)],
                        Objects.requireNonNull(npcSet.stream().findFirst().orElse(null)).getAnnounceName()
                )).build();
                Match newMatch = Match.builder()
                .human_participants(wrestlerSet)
                .npc_participants(npcSet)
                .winner(winner.getAnnounceName())
                .condition(matchService.defineCondition())
                .matchEvents(Set.of(matchEvent))
                .build();
        matchService.createMatch(newMatch);
        CreateMatchResponse matchResponse = CreateMatchResponse.builder().winnerName(winner.getAnnounceName()).build();
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }
}
