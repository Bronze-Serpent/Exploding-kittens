package com.kittens.server.dto;

import com.kittens.logic.card.CardName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayCombinationDto
{
    List<CardName> cardNames;
    Long playerId;
}
