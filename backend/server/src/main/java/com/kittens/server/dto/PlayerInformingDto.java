package com.kittens.server.dto;

import com.kittens.logic.action.player.interaction.PlayerInformer;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerInformingDto
{
    PlayerInformer.Informing infoType;
    String msg;
}
