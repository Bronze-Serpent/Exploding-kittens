package com.kittens.server.service;

import com.kittens.server.dto.PlayCardDto;

import java.util.Optional;


public interface UserQuestioner {

    Optional<PlayCardDto> askPlayersToPlaySuddenCards(Long roomId, Object message);

    void responseToPlaySuddenCards(Long roomId, PlayCardDto dto);

}
