package com.kittens.server.dto;

import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerQuestioningDto
{
    PlayerQuestioner.Question question;
}
