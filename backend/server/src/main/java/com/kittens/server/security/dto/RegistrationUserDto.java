package com.kittens.server.security.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDto {
    String login;
    String password;
    String passwordConfirmation;
    List<Integer> passwordEnterValueTime;
    List<Integer> passwordConfirmationEnterValueTime;
}
