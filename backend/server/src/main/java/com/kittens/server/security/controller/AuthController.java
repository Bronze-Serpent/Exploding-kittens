package com.kittens.server.security.controller;

import com.kittens.server.security.dto.JwtRequestDto;
import com.kittens.server.security.dto.JwtResponseDto;
import com.kittens.server.security.dto.RefreshJwtRequestDto;
import com.kittens.server.security.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/token")
    public JwtResponseDto getNewAccessToken(@RequestBody RefreshJwtRequestDto request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponseDto getNewRefreshToken(@RequestBody RefreshJwtRequestDto request) {
        return authService.refresh(request.getRefreshToken());
    }
}
