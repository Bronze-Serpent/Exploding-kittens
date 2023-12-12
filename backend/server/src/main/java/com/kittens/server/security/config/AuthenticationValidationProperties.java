package com.kittens.server.security.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@ConfigurationProperties(prefix = "authentication.validation")
public class AuthenticationValidationProperties {
    Integer lastAuthTimes = 5;
    Double possibleDelta = 2.0;
}
