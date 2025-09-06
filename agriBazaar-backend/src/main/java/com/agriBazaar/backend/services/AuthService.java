package com.agriBazaar.backend.services;

import com.agriBazaar.backend.config.AuthUtil;
import com.agriBazaar.backend.dto.LoginRequestDto;
import com.agriBazaar.backend.dto.LoginResponseDto;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public LoginResponseDto signup(LoginRequestDto loginRequestDto){
        userRepository.findByUsername(loginRequestDto.getUsername())
                .ifPresent(u->{throw new IllegalArgumentException("User already exists");});

        User user=User.builder()
                .username(loginRequestDto.getUsername())
                .password(passwordEncoder.encode(loginRequestDto.getPassword()))
                .email(loginRequestDto.getEmail())
                .build();

        user=userRepository.save(user);

        String token=authUtil.accessToken(user);

        return new LoginResponseDto(token,user.getId());
    }
}
