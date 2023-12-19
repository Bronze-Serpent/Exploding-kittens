package com.kittens.server.service;

import com.kittens.server.game.model.DbRefGameState;

public interface GameStateService {

    DbRefGameState getGameStateByRoomId(Long roomId);
}
