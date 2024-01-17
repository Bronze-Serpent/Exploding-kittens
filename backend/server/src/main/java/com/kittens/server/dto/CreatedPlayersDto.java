package com.kittens.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatedPlayersDto
{
    Map<Long, Long> playerIdToUserId;
}
