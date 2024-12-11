package com.efederation.Service.impl;

import com.efederation.Constants.CommonConstants;
import com.efederation.Enums.Role;
import com.efederation.DTO.AuthenticationRequest;
import com.efederation.DTO.AuthenticationResponse;
import com.efederation.DTO.RegisterRequest;
import com.efederation.Model.RefreshToken;
import com.efederation.Model.User;
import com.efederation.Repository.RefreshTokenRepository;
import com.efederation.Repository.UserRepository;
import com.efederation.Service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private static final Duration REFRESH_TOKEN_VALIDITY = Duration.ofHours(24);

    @Autowired
    EmailService emailService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    CommonConstants constants;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public String generateRefreshToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        String newToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(newToken);
        refreshToken.setExpirationTime(OffsetDateTime.now().plus(REFRESH_TOKEN_VALIDITY));
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        return newToken;
    }


    public User validateRefreshTokenGetUser(String refreshToken) {
        RefreshToken locatedToken = refreshTokenRepository.findByTokenAndExpirationTimeAfter(refreshToken, OffsetDateTime.now());
        return locatedToken.getUser();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var role = request.getRole() == 1 ? Role.GENERAL : Role.ADMIN;
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isEmpty()) {
            userRepository.save(user);
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = generateRefreshToken(user.getEmail());
        try {
            emailService.sendEmailVerification(user.getEmail(), constants.getFromEmail());
        } catch(MessagingException e) {
            e.printStackTrace();
        }
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
        } catch(UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if(user.isValidated()) {
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = user.getRefreshToken();
            return  AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken.getToken()).build();
        } else {
            throw new BadCredentialsException("User is not validated");
        }
    }
}
