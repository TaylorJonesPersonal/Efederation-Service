package com.efederation.Controller;

import com.efederation.DTO.AuthenticationRequest;
import com.efederation.DTO.AuthenticationResponse;
import com.efederation.DTO.RefreshRequest;
import com.efederation.DTO.RegisterRequest;
import com.efederation.Exception.ApiError;
import com.efederation.Exception.RefreshTokenExpiredException;
import com.efederation.Model.User;
import com.efederation.Service.UserService;
import com.efederation.Service.impl.AuthServiceImpl;
import com.efederation.Service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    UserService userService;

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<String> handleAuthenticationException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler({RefreshTokenExpiredException.class})
    @ResponseBody
    public ResponseEntity<Object> handleRefreshTokenExpired(
            RefreshTokenExpiredException ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("{\"status\": \"UP\"}");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/validate/{email}")
    public ResponseEntity<String> validate(@PathVariable String email) {
        userService.enableAccountByEmail(email);
        return ResponseEntity.ok("Email validated");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws RefreshTokenExpiredException {
        User validatedUser = authService.validateRefreshTokenGetUser(request.getRefreshToken());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setRefreshToken(request.getRefreshToken());
        response.setToken(jwtService.generateToken(validatedUser));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
