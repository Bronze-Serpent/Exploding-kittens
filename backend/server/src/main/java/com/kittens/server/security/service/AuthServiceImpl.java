package com.kittens.server.security.service;

import com.kittens.server.user.entity.User;
import com.kittens.server.user.service.UserService;
import com.kittens.server.security.dto.JwtRequestDto;
import com.kittens.server.security.dto.JwtResponseDto;
import com.kittens.server.security.dto.RegistrationUserDto;
import com.kittens.server.security.exception.AuthException;
import com.kittens.server.security.exception.RegistrationException;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    JwtProvider jwtProvider;
    Map<String, String> refreshStorage = new HashMap<>();
    UserService userService;

    @Override
    public Boolean register(@NotNull @Valid RegistrationUserDto dto) {
        if (!Objects.equals(dto.getPassword(), dto.getPasswordConfirmation())) {
            throw new RegistrationException("Пароли не совпадают");
        }
        userService.getByLogin(dto.getLogin()).orElseThrow(() -> new RegistrationException("Пользователь с таким логином существует"));
        return userService.registerUser(dto);
    }

    public JwtResponseDto login(@NotNull @Valid JwtRequestDto authRequest) {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        final String passwordHash = MD5Encoder.encode(authRequest.getPassword().getBytes());
        //TODO: проверить динамику ввода

        if (user.getPasswordHash().equals(passwordHash)) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponseDto getAccessToken(@NotNull @Valid String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    public JwtResponseDto refresh(@NotNull @Valid String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }
}
