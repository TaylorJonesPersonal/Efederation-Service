package com.efederation.Controller;

import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchAttributesResponse;
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
    MatchService matchService;


    @GetMapping("/{wrestlerId}")
    public ResponseEntity<List<MatchAttributesResponse>> getMatches(@PathVariable int wrestlerId) {
        List<MatchAttributesResponse> matchResponse = matchService.getMatches(wrestlerId);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }


    //refactor - move all of this to a service. Should not be in a controller.
    @PostMapping("/create")
    public ResponseEntity<CreateMatchResponse> createMatch(@RequestBody CreateMatchRequest matchRequest) {
        CreateMatchResponse matchResponse = matchService.createMatch(matchRequest);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }
}
