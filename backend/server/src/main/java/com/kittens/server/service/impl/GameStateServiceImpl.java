package com.kittens.server.service.impl;

import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.mapper.GsEntityToRoomGs;
import com.kittens.server.mapper.RoomGsToGsEntity;
import com.kittens.server.repository.GameStateRepository;
import com.kittens.server.service.GameStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class GameStateServiceImpl implements GameStateService
{
    private final GameStateRepository gameStateRepository;

    private final GsEntityToRoomGs entityToRoomGs;

    private final RoomGsToGsEntity roomGsToEntity;


    @Override
    public Optional<RoomGameState> getGameStateByRoomId(Long roomId)
    {
        return gameStateRepository.findGameStateByRoomId(roomId)
                .map(entityToRoomGs::map);
    }

    @Override
    public void updateGameState(RoomGameState gameState)
    {
        GameStateEntity gameStateEntity = gameStateRepository.findById(gameState.getId())
                .orElseThrow(() -> new RuntimeException("Объект ссылается на gameState с несуществующим id"));

        roomGsToEntity.copy(gameState, gameStateEntity);
        gameStateRepository.saveAndFlush(gameStateEntity);
    }
}
