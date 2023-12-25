package com.kittens.server.service;

import com.kittens.server.dto.PlayCardDto;


public interface GameService
{
    void playCard(Long roomId, PlayCardDto dto);

    Long createRoom(Long userId);

    void addUserToRoom(Long roomId, Long userId);

    void initGameStateInRoom(Long roomId);
}
