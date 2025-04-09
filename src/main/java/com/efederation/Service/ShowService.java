package com.efederation.Service;

import com.efederation.DTO.AddCharacterToShowRequest;
import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateShowRequest;
import com.efederation.DTO.ShowResponse;
import com.efederation.Exception.NoCharacterFoundException;
import com.efederation.Exception.ShowContainsCharacterException;
import com.efederation.Exception.ShowNotFoundException;
import com.efederation.Model.Show;

import java.io.IOException;
import java.util.List;

public interface ShowService {
    ApiResponse<List<ShowResponse>> getShowsByCharacterId(String id);
    ApiResponse<Show> createShow(CreateShowRequest request) throws IOException;
    ApiResponse<Show> addCharacter(AddCharacterToShowRequest request) throws ShowNotFoundException, NoCharacterFoundException, ShowContainsCharacterException;
}
