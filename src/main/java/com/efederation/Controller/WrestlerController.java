package com.efederation.Controller;

import com.efederation.DTO.SubmitWrestlerRequest;
import com.efederation.DTO.SubmitWrestlerResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import com.efederation.Repository.UserRepository;
import com.efederation.Repository.WrestlerRepository;
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
    WrestlerRepository wrestlerRepository;

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
    public ResponseEntity<SubmitWrestlerResponse> createWrestler(@RequestHeader("Authorization") String authHeader,
                                                                 @RequestBody SubmitWrestlerRequest submitWrestlerRequest) {
        String username = jwtService.extractUsername(authHeader.replaceAll("Bearer ", ""));
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional
                .map(user -> new ResponseEntity<>(
                        wrestlerService.createWrestler(user, submitWrestlerRequest),
                        HttpStatus.CREATED)
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateWrestler() {
        wrestlerService.updateWrestlerJsonAttributes(1);
        return ResponseEntity.ok().body("Wrestler updated!");
    }

    @GetMapping(value="/image/{wrestlerId}")
    public ResponseEntity<String> getBase64IMage(@PathVariable long wrestlerId) {
        return new ResponseEntity<>(wrestlerService.getBase64Image(wrestlerId), HttpStatus.OK);
    }

    @PostMapping(value = "/image/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("wrestlerId") long id) throws IOException {
        wrestlerService.uploadImage(id, file);
        return ResponseEntity.ok().body("Image uploaded!");
    }
}
