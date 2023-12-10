package com.kittens.server.security.service;

import com.kittens.server.security.dto.JwtRequestDto;
import com.kittens.server.security.dto.JwtResponseDto;
import com.kittens.server.security.dto.RegistrationUserDto;

public interface AuthService {

    Boolean register(RegistrationUserDto dto);
    JwtResponseDto login(JwtRequestDto jwtRequestDto);

    JwtResponseDto getAccessToken(String refreshToken);

    JwtResponseDto refresh(String refreshToken);


}
