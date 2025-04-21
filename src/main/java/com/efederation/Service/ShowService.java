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

public interface ShowService {
    ApiResponse<ShowResponse> getShowsByCharacterId(String id);
    ApiResponse<Show> createShow(CreateShowRequest request) throws IOException;
    ApiResponse<Show> addCharacter(AddCharacterToShowRequest request) throws ShowNotFoundException, NoCharacterFoundException, ShowContainsCharacterException;
}
