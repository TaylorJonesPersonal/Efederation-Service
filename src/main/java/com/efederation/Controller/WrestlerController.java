package com.efederation.Controller;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.DTO.WrestlerImageCreateRequest;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Exception.ApiError;
import com.efederation.Exception.ImageSetNotFoundException;
import com.efederation.Exception.UserNotFoundException;
import com.efederation.Model.User;
import com.efederation.Repository.UserRepository;
import com.efederation.Service.impl.JwtServiceImpl;
import com.efederation.Service.WrestlerService;
import com.efederation.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
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

    @Autowired
    CommonUtils commonUtils;

    @ExceptionHandler({ImageSetNotFoundException.class, UserNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleImageSetNotFoundException(
            Exception ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

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
                                                                  @RequestBody SubmitCharacterRequest submitCharacterRequest) throws ImageSetNotFoundException, UserNotFoundException {
        String username = jwtService.extractUsername(authHeader.replaceAll("Bearer ", ""));
        Optional<User> userOptional = userRepository.findByEmail(username);
        return new ResponseEntity<>(
                wrestlerService.createWrestler(
                        userOptional.orElseThrow(
                                () -> new UserNotFoundException("User not found")
                        ), submitCharacterRequest
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value= "/update", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateWrestler() {
        wrestlerService.updateWrestlerJsonAttributes(1);
        return ResponseEntity.ok().body("Wrestler updated!");
    }
}
