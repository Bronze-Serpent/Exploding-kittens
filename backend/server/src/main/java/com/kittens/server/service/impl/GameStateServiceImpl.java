package com.kittens.server.service.impl;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.server.entity.CardDeck;
import com.kittens.server.entity.CardReset;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.mapper.GsEntityToRoomGs;
import com.kittens.server.mapper.RoomGsToGsEntity;
import com.kittens.server.repository.*;
import com.kittens.server.service.GameStateService;
import com.kittens.server.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class GameStateServiceImpl implements GameStateService
{
    private final GameStateRepository gameStateRepository;
    private final RoomRepository roomRepository;
    private final PlayerService playerService;

    private final GsEntityToRoomGs entityToRoomGs;
    private final RoomGsToGsEntity roomGameStateToEntity;


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

        roomGameStateToEntity.copy(gameState, gameStateEntity);
        gameStateRepository.saveAndFlush(gameStateEntity);
    }

    @Override
    public void saveNewGameState(Long roomId, RoomGameState gameState)
    {
        ArrayList<PlayerQueuePointer> emptyPointers = new ArrayList<>();
        for (int i = 0; i < gameState.getPlayersTurn().size(); i++)
            emptyPointers.add(new PlayerQueuePointer());

        GameStateEntity createdGameState = GameStateEntity.builder()
                .cardDeck(new CardDeck())
                .cardReset(new CardReset())
                .playerQueuePointers(emptyPointers)
                .room(roomRepository.findById(roomId)
                        .orElseThrow(() -> new RuntimeException("Невозможно сохранить gameState для комнаты с таким roomId: "
                                + roomId + " поскольку комнаты с таким id не существует")))
                .build();

        roomGameStateToEntity.fillEmptyEntity(gameState, createdGameState);

        playerService.updatePlayers(gameState.getPlayersTurn().getElements().stream()
                .map(abstractPlayer -> (UserRefPlayer) abstractPlayer)
                .collect(Collectors.toSet()));
        gameStateRepository.saveAndFlush(createdGameState);
    }

    @Override
    public Long createEmptyGameState()
    {
        return gameStateRepository.save(new GameStateEntity())
                .getId();
    }
}
