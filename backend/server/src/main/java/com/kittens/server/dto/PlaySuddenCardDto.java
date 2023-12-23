package com.kittens.server.dto;

import com.kittens.logic.card.CardName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaySuddenCardDto {
    CardName cardName;
    Long userId;
}
