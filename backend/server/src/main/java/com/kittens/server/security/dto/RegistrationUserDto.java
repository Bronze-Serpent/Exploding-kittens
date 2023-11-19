package com.kittens.server.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class RegistrationUserDto {
    @NotBlank
    String login;
    @NotBlank
    String password;
    @NotBlank
    String passwordConfirmation;
    @NotEmpty
    List<Integer> passwordEnterValueTime;
    @NotEmpty
    List<Integer> passwordConfirmationEnterValueTime;
}
