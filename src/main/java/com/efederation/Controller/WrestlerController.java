package com.efederation.Controller;

import com.efederation.DTO.WrestlerRequest;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.UserRepository;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.impl.JwtServiceImpl;
import com.efederation.Service.impl.WrestlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<WrestlerResponse> createWrestler(@RequestHeader("Authorization") String authHeader,
                                                           @RequestBody WrestlerRequest wrestlerRequest) {
        String username = jwtService.extractUsername(authHeader.replaceAll("Bearer ", ""));
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isPresent()) {
            Wrestler newWrestler = wrestlerService.createWrestler(userOptional.get(), wrestlerRequest);
            return new ResponseEntity<>(new WrestlerResponse("Successful", newWrestler.getAnnounceName()), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }
}
