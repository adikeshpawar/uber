package com.example.user_service.services;

import com.example.user_service.dtos.RideRequestDto;
import com.example.user_service.dtos.UserLoginDto;
import com.example.user_service.dtos.UserRegisterDto;
import com.example.user_service.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User register(UserRegisterDto registerDto);

    String login(UserLoginDto loginDto);

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByEmail(String email);

}
