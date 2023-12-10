package com.kittens.server.security.controller;

import com.kittens.server.security.dto.RegistrationUserDto;
import com.kittens.server.security.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationController {
    AuthService authService;

    @PostMapping("/")
    public Boolean registration(@RequestBody @NotNull @Valid  RegistrationUserDto registrationUserDto){
        return authService.register(registrationUserDto);
    }
}
