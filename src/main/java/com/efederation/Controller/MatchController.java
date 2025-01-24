package com.efederation.Controller;

import com.efederation.Constants.CommonConstants;
import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchResponse;
import com.efederation.Model.Character;
import com.efederation.Model.Match;
import com.efederation.Model.NPC;
import com.efederation.Model.Wrestler;
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

    @GetMapping("/{wrestlerId}")
    public ResponseEntity<MatchResponse> getMatches(@PathVariable int wrestlerId) {
        MatchResponse matchResponse = matchService.getMatches(wrestlerId);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateMatchResponse> createMatch(@RequestBody CreateMatchRequest matchRequest) {
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
        Match newMatch = Match.builder().human_participants(wrestlerSet).npc_participants(npcSet).winner(winner.getAnnounceName()).build();
        matchService.createMatch(newMatch);
        CreateMatchResponse matchResponse = CreateMatchResponse.builder().winnerName(winner.getAnnounceName()).build();
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }
}
