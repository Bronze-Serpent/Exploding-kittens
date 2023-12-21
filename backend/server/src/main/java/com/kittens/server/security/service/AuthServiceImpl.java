package com.kittens.server.security.service;

import com.kittens.server.security.config.AuthenticationValidationProperties;
import com.kittens.server.security.dto.JwtRequestDto;
import com.kittens.server.security.dto.JwtResponseDto;
import com.kittens.server.security.dto.RegistrationUserDto;
import com.kittens.server.security.exception.AuthException;
import com.kittens.server.security.exception.RegistrationException;
import com.kittens.server.user.entity.User;
import com.kittens.server.user.service.UserDynamicPasswordService;
import com.kittens.server.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.MD5Digest;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    JwtProvider jwtProvider;
    Map<String, String> refreshStorage = new HashMap<>();
    UserService userService;
    UserDynamicPasswordService userDynamicPasswordService;
    AuthenticationValidationProperties authenticationValidationProperties;

    @Override
    public Boolean register(@NotNull @Valid RegistrationUserDto dto) {
        if (!Objects.equals(dto.getPassword(), dto.getPasswordConfirmation())) {
            throw new RegistrationException("Пароли не совпадают");
        }
        if (userService.existsByLogin(dto.getLogin())) {
            throw new RegistrationException("Пользователь с таким логином уже существует");
        }

        return userService.registerUser(dto);
    }

    public JwtResponseDto login(@NotNull @Valid JwtRequestDto authRequest) {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        final String passwordHash = new String(MD5Digest.encode(authRequest.getLogin().getBytes(), authRequest.getPassword().getBytes(), new byte[0]));

        if (user.getPasswordHash().equals(passwordHash) && validateTries(authRequest.getLogin(), authRequest.getPasswordEnterValueTime())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            userService.addDynamicPassword(user, authRequest.getPasswordEnterValueTime());
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponseDto(accessToken, refreshToken, user.getId());
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
                return new JwtResponseDto(accessToken, null, user.getId());
            }
        }
        return new JwtResponseDto(null, null, null);
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
                return new JwtResponseDto(accessToken, newRefreshToken, user.getId());
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    private boolean validateTries(String userLogin, List<Integer> currentTry) {
        List<Integer[]> topByUserLogin = userDynamicPasswordService.findTopByUserLogin(authenticationValidationProperties.getLastAuthTimes(), userLogin);
        double averageOfPreviousTries = topByUserLogin.stream()
                .map(array -> Arrays.stream(array)
                        .mapToDouble(a -> a)
                        .average()
                        .orElse(Double.MAX_VALUE))
                .mapToDouble(a -> a)
                .average()
                .orElse(Double.MAX_VALUE);
        double averageCurrentTry = currentTry.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(Double.MIN_VALUE);
        log.info("Previous: {}, Current: {}", averageOfPreviousTries, averageCurrentTry);
        return Math.abs(averageCurrentTry - averageOfPreviousTries) <= authenticationValidationProperties.getPossibleDelta();
    }
}
