package com.kittens.server.db.repository;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.repository.GameStateRepository;
import com.kittens.server.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class GameStateRepositoryTest extends DatabaseTest
{
    private final GameStateRepository gameStateRepository;

    private final PlayerRepository playerRepository;

    private final EntityManager entityManager;


    @Test
    public void shouldRemoveObsoletePointers()
    {
        GameStateEntity oldStateEntity = gameStateRepository.findById(1L).get();
        PlayerEntity player1 = playerRepository.findById(1L).get();
        PlayerEntity player2 = playerRepository.findById(2L).get();

        // TODO: 13.12.2023 У нас удаляются строки из game_state или обновляются? Аналогично с pointer
        PlayerQueuePointer playerQueuePointer = new PlayerQueuePointer(4L, player2, player1);

        GameStateEntity gameState = GameStateEntity.builder()
                .playerQueuePointers(List.of(playerQueuePointer))
                .build();

        // TODO: 13.12.2023 не работает SuperBuilder почему-то
        gameState.setId(oldStateEntity.getId());

        GameStateEntity newGameState = gameStateRepository.save(gameState);

        assertThat(newGameState.getPlayerQueuePointers()).containsExactlyElementsOf(List.of(playerQueuePointer));

        // TODO: 13.12.2023  т.е. таблица с pointer захламляется т.к. оттуда ничего не удаляется, если мы просто обновляем GameState
        assertThat(entityManager.find(PlayerQueuePointer.class, 1L)).isNull();
        assertThat(entityManager.find(PlayerQueuePointer.class, 2L)).isNull();
        assertThat(entityManager.find(PlayerQueuePointer.class, 3L)).isNull();
    }
}
