package com.kittens.server.security.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto {
    String login;
    String password;
    List<Integer> passwordEnterValueTime;
}
