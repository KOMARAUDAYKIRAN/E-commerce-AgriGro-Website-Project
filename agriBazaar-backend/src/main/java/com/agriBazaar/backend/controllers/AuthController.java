package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.dto.LoginRequestDto;
import com.agriBazaar.backend.dto.LoginResponseDto;
import com.agriBazaar.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63342/E-commerce-AgriGro-Website-Project4/agriBazzar-frontend/login.html")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<?> signup(@RequestBody LoginRequestDto loginRequestDto){
       try{
           return ResponseEntity.ok(authService.signup(loginRequestDto));
       }catch (Exception exception){
           return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body(Map.of("error","This username is already taken. Please choose a different one."));
       }
    }
}
