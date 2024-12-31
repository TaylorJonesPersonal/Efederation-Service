package com.efederation.Controller;

import com.efederation.DTO.NPCResponse;
import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.Service.NPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1/npc")
public class NPCController {

    @Autowired
    NPCService npcService;


    @GetMapping
    public ResponseEntity<List<NPCResponse>> getNPCs() {
        return new ResponseEntity<>(npcService.getNPCs(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SubmitCharacterResponse> createNPC(@RequestBody SubmitCharacterRequest submitCharacterRequest) {
        return new ResponseEntity<>(npcService.createNPC(submitCharacterRequest), HttpStatus.CREATED);
    }

    @PostMapping(value= "/update", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateNPC() {
        npcService.updateNPCJsonAttributes(1);
        return ResponseEntity.ok().body("NPC updated!");
    }

    @PostMapping(value = "/image/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("npcId") long id) {
        npcService.uploadImage(id, file);
        return ResponseEntity.ok().body("Image uploaded!");
    }
}
