package com.efederation.Controller;

import com.efederation.DTO.CreateMatchRequest;
import com.efederation.DTO.CreateMatchResponse;
import com.efederation.DTO.MatchAttributesResponse;
import com.efederation.Exception.ApiError;
import com.efederation.Exception.MatchCreationException;
import com.efederation.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
@RequestMapping("/api/v1/match")
public class MatchController {


    @Autowired
    MatchService matchService;

    @ExceptionHandler({MatchCreationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleMatchCreationFailure(
            MatchCreationException ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred in match creation.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @GetMapping("/{wrestlerId}")
    public ResponseEntity<List<MatchAttributesResponse>> getMatches(@PathVariable int wrestlerId) throws MatchCreationException {
        List<MatchAttributesResponse> matchResponse = matchService.getMatches(wrestlerId);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }


    //refactor - move all of this to a service. Should not be in a controller.
    @PostMapping("/create")
    public ResponseEntity<CreateMatchResponse> createMatch(@RequestBody CreateMatchRequest matchRequest) throws MatchCreationException {
        CreateMatchResponse matchResponse = matchService.createMatch(matchRequest);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }
}
