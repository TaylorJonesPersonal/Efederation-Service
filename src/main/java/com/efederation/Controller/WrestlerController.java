package com.efederation.Controller;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import com.efederation.Repository.UserRepository;
import com.efederation.Service.impl.JwtServiceImpl;
import com.efederation.Service.WrestlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wrestler")
public class WrestlerController {

    @Autowired
    WrestlerService wrestlerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtServiceImpl jwtService;

    @GetMapping
    public ResponseEntity<List<WrestlerResponse>> getWrestlers(@RequestHeader("Authorization") String authHeader) {
        String username = jwtService.extractUsername(authHeader.replaceAll("Bearer ", ""));
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional
                .map(user -> new ResponseEntity<>(wrestlerService.getWrestlers(user), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<SubmitCharacterResponse> createWrestler(@RequestHeader("Authorization") String authHeader,
                                                                  @RequestBody SubmitCharacterRequest submitCharacterRequest) {
        String username = jwtService.extractUsername(authHeader.replaceAll("Bearer ", ""));
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional
                .map(user -> new ResponseEntity<>(
                        wrestlerService.createWrestler(user, submitCharacterRequest),
                        HttpStatus.CREATED)
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value= "/update", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateWrestler() {
        wrestlerService.updateWrestlerJsonAttributes(1);
        return ResponseEntity.ok().body("Wrestler updated!");
    }

    @PostMapping(value = "/image/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("wrestlerId") long id) {
        wrestlerService.uploadImage(id, file);
        return ResponseEntity.ok().body("Image uploaded!");
    }
}
