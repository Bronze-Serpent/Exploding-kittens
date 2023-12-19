package com.kittens.server.service;

import com.kittens.server.game.model.RoomGameState;

import java.util.Optional;


public interface GameStateService
{
    Optional<RoomGameState> getGameStateByRoomId(Long roomId);

    void updateGameState(RoomGameState gameState);
}
