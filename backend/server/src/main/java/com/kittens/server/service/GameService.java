package com.kittens.server.service;

import com.kittens.server.dto.PlayCardDto;


public interface GameService
{
    void playCard(Long roomId, PlayCardDto dto);

    void initGameInRoom(Long roomId);
}
