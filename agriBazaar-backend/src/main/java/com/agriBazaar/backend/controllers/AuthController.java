package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.dto.LoginRequestDto;
import com.agriBazaar.backend.dto.LoginResponseDto;
import com.agriBazaar.backend.dto.SignupRequestDto;
import com.agriBazaar.backend.dto.SignupResponseDto;
import com.agriBazaar.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63342/E-commerce-AgriGro-Website-Project4/agriBazzar-frontend/login.html")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            LoginResponseDto responseDto=authService.login(loginRequestDto);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        try{
            SignupResponseDto responseDto=authService.signup(signupRequestDto);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
