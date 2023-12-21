package com.kittens.server.security.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private final String type = "Bearer";
    String accessToken;
    String refreshToken;
    Long userId;
}
