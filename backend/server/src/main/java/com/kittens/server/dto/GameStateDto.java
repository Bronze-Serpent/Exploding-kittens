package com.kittens.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameStateDto
{
    Long playerTurnId;
    int stepQuantity;
    List<GameStatePlayerDto> players;
}
