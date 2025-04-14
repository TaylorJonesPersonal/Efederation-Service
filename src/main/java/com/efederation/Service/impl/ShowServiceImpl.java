package com.efederation.Service.impl;

import com.efederation.DTO.AddCharacterToShowRequest;
import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateShowRequest;
import com.efederation.DTO.ShowResponse;
import com.efederation.Enums.Day;
import com.efederation.Enums.Importance;
import com.efederation.Exception.NoCharacterFoundException;
import com.efederation.Exception.ShowContainsCharacterException;
import com.efederation.Exception.ShowNotFoundException;
import com.efederation.Model.NPC;
import com.efederation.Model.Show;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.NPCRepository;
import com.efederation.Repository.ShowRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.ShowService;
import com.efederation.Utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final NPCRepository npcRepository;
    private final WrestlerRepository wrestlerRepository;

    private final CommonUtils commonUtils;

    @Autowired
    public ShowServiceImpl(ShowRepository showRepository,
                           NPCRepository npcRepository,
                           WrestlerRepository wrestlerRepository,
                           CommonUtils commonUtils
    ) {
        this.showRepository = showRepository;
        this.npcRepository = npcRepository;
        this.wrestlerRepository = wrestlerRepository;
        this.commonUtils = commonUtils;
    }

    @Transactional
    public ApiResponse<List<ShowResponse>> getShowsByCharacterId(String id) {
        List<Show> shows = showRepository.getShowsByWrestlerId(Integer.parseInt(id));
        List<ShowResponse> showResponses = new ArrayList<>();
        ApiResponse<List<ShowResponse>> response = new ApiResponse<>();
        for(Show show : shows) {
            ShowResponse newShowResponse = ShowResponse
                    .builder()
                    .id(show.getId())
                    .name(show.getName())
                    .defaultImage(commonUtils.getBase64Image(show.getDefaultImage()))
                    .logo(commonUtils.getBase64Image(show.getLogoImage()))
                    .importance(String.valueOf(show.getImportance()))
                    .day(String.valueOf(show.getDay()))
                    .build();
            showResponses.add(newShowResponse);
        }
        response.setMessage("Successfully retrieved shows");
        response.setData(showResponses);
        response.setStatus(HttpStatus.OK.toString());
        return response;
    }

    public ApiResponse<Show> createShow(CreateShowRequest request) throws IOException {
        Show newShow = Show
                .builder()
                .name(request.getName())
                .day(Day.valueOf(request.getDay()))
                .defaultImage(request.getImage().getBytes())
                .logoImage(request.getLogo().getBytes())
                .importance(Importance.valueOf(request.getImportance()))
                .build();
        showRepository.save(newShow);
        ApiResponse<Show> response = new ApiResponse<>();
        response.setData(newShow);
        response.setStatus(HttpStatus.CREATED.toString());
        response.setMessage("Successfully Created");
        return response;
    }

    public ApiResponse<Show> addCharacter(AddCharacterToShowRequest request) throws ShowNotFoundException, NoCharacterFoundException, ShowContainsCharacterException {
        NPC newShowNPC;
        Wrestler newShowWrestler;
        Show show = showRepository.findById(request.getShow_id()).orElseThrow(
                () -> new ShowNotFoundException("No results for show id.")
        );
        if(request.isNpc()) {
            newShowNPC = npcRepository.findById(request.getCharacter_id()).orElseThrow(
                    () -> new NoCharacterFoundException("No results for character.")
            );
            if(show.getNpcsContracted().contains(newShowNPC))
                throw new ShowContainsCharacterException("Show already contains character.");
            show.getNpcsContracted().add(newShowNPC);
        } else {
            newShowWrestler = wrestlerRepository.findById(request.getCharacter_id()).orElseThrow(
                    () -> new NoCharacterFoundException("No results for character.")
            );
            if(show.getWrestlersContracted().contains(newShowWrestler))
                throw new ShowContainsCharacterException("Show already contains character.");
            show.getWrestlersContracted().add(newShowWrestler);
        }
        showRepository.save(show);
        ApiResponse<Show> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.toString());
        response.setData(show);
        response.setMessage("Show updated with given character.");
        return response;
    }
}
