package com.kittens.server.service;

import com.kittens.server.dto.PlaySuddenCardDto;

import java.util.Optional;

public interface UserQuestioner {

    Optional<PlaySuddenCardDto> askPlayersToPlaySuddenCards(Long roomId, Object message);

    void responseToPlaySuddenCards(Long roomId, PlaySuddenCardDto dto);

}
