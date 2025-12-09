package com.example.user_service.controller;

import com.example.user_service.dtos.UserLoginDto;
import com.example.user_service.dtos.UserRegisterDto;
import com.example.user_service.dtos.LoginResponseDto;
import com.example.user_service.entity.User;
import com.example.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final com.example.user_service.services.UserService  userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @Valid @RequestBody UserRegisterDto dto
    ) {
        User user = userService.register(dto);
        String token = userService.login(
                new UserLoginDto(dto.getEmail(), dto.getPassword())
        );
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody UserLoginDto dto
    ) {
        String token = userService.login(dto);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
