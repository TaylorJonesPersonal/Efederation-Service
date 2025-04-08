package com.efederation.Service.impl;

import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateShowRequest;
import com.efederation.Enums.Importance;
import com.efederation.Model.Show;
import com.efederation.Repository.ShowRepository;
import com.efederation.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    public ApiResponse<Show> createShow(CreateShowRequest request) throws IOException {
        Show newShow = Show
                .builder()
                .name(request.getName())
                .defaultImage(request.getImage().getBytes())
                .importance(Importance.valueOf(request.getImportance()))
                .build();
        showRepository.save(newShow);
        ApiResponse<Show> response = new ApiResponse<>();
        response.setData(newShow);
        response.setStatus(HttpStatus.CREATED.toString());
        response.setMessage("Successfully Created");
        return response;
    }
}
