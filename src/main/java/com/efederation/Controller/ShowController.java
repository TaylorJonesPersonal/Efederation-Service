package com.efederation.Controller;

import com.efederation.DTO.AddCharacterToShowRequest;
import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateShowRequest;
import com.efederation.DTO.ShowResponse;
import com.efederation.Exception.NoCharacterFoundException;
import com.efederation.Exception.ShowContainsCharacterException;
import com.efederation.Exception.ShowNotFoundException;
import com.efederation.Model.Show;
import com.efederation.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/show")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping(value = "/v1/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<ShowResponse>> getShowsByCharacterId(@PathVariable String id) {
        ApiResponse<ShowResponse> showsResponse = showService.getShowsByCharacterId(id);
        return new ResponseEntity<>(showsResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Show>> createShow(@ModelAttribute CreateShowRequest request) throws IOException {
        ApiResponse<Show> showResponse = showService.createShow(request);
        return new ResponseEntity<>(showResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/v1/addchar", consumes={MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Show>> addCharacter(@RequestBody AddCharacterToShowRequest request) throws
            ShowNotFoundException,
            ShowContainsCharacterException,
            NoCharacterFoundException {
        ApiResponse<Show> showUpdateResponse = showService.addCharacter(request);
        return new ResponseEntity<>(showUpdateResponse, HttpStatus.OK);
    }
}
