package com.efederation.Service.impl;

import com.efederation.Enums.Role;
import com.efederation.Model.AuthenticationRequest;
import com.efederation.Model.AuthenticationResponse;
import com.efederation.Model.RegisterRequest;
import com.efederation.Model.User;
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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    @Autowired
    private EmailService emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

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
        try {
            emailService.sendEmailVerification("jonesftwingp@outlook.com", "efedbiz@outlook.com");
        } catch(MessagingException e) {
            e.printStackTrace();
        }
        return AuthenticationResponse.builder().token(jwtToken).build();
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
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder().token(jwtToken).build();
    }
}
