package com.example.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private String tokenType = "Bearer";

    // custom constructor required by AuthController
    public LoginResponseDto(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }
}
