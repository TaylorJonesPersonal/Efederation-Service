package com.efederation.Controller;

import com.efederation.DTO.NPCResponse;
import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.Enums.ImageType;
import com.efederation.Exception.ApiError;
import com.efederation.Exception.ImageSetNotFoundException;
import com.efederation.Exception.UserNotFoundException;
import com.efederation.Service.NPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1/npc")
public class NPCController {

    @Autowired
    NPCService npcService;

    @ExceptionHandler({ImageSetNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleImageSetNotFoundException(
            ImageSetNotFoundException ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @GetMapping
    public ResponseEntity<List<NPCResponse>> getNPCs() {
        return new ResponseEntity<>(npcService.getNPCs(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SubmitCharacterResponse> createNPC(@RequestBody SubmitCharacterRequest submitCharacterRequest)
            throws ImageSetNotFoundException {
        return new ResponseEntity<>(npcService.createNPC(submitCharacterRequest), HttpStatus.CREATED);
    }

    @PostMapping(value= "/update", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateNPC() {
        npcService.updateNPCJsonAttributes(1);
        return ResponseEntity.ok().body("NPC updated!");
    }
}
