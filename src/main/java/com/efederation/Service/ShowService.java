package com.efederation.Service;

import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateShowRequest;
import com.efederation.Model.Show;

import java.io.IOException;

public interface ShowService {
    ApiResponse<Show> createShow(CreateShowRequest request) throws IOException;
}
