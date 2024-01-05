package com.kittens.server.service;

import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.dto.UserAnswerDto;

import java.util.Optional;


public interface UserQuestioner extends PlayerQuestioner
{
    Optional<PlayCardDto> askPlayersToPlaySuddenCards(Long roomId, Object message);

    void responseToPlaySuddenCards(Long roomId, PlayCardDto dto);

    void responseToQuestion(Long roomId, UserAnswerDto answerDto);
}
