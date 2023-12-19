package com.kittens.server.integration.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.DbRefGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.integration.IntegrationTest;
import com.kittens.server.mapper.DbRefGsToGsEntity;
import com.kittens.server.mapper.GsEntityToDbRefGs;
import com.kittens.server.mapper.UserRefPlayerToPlayerEntity;
import com.kittens.server.repository.GameStateRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor
public class GameStateMappersTest extends IntegrationTest
{
    private final GsEntityToDbRefGs entityToGameState;
    private final DbRefGsToGsEntity gameStateToEntity;
    private final UserRefPlayerToPlayerEntity playerToPlayerEntity;

    private final GameStateRepository gameStateRepository;


    @Test
    public void shouldMapGameStateEntityToGameState()
    {
        GameStateEntity gameStateEntity = gameStateRepository.findById(1L).get();

        DbRefGameState gameState = entityToGameState.map(gameStateEntity);

        assertThat(gameState.getId()).isEqualTo(gameStateEntity.getId());
        assertThat(gameState.getCardDeck().stream()
                .map(Card::getName)
                .toList()).containsExactlyInAnyOrder(gameStateEntity.getCardDeck().getValue());
        assertThat(gameState.getCardReset().stream()
                .map(Card::getName)
                .toList()).containsExactlyInAnyOrder(gameStateEntity.getCardReset().getValue());
        assertThat(gameState.getStepQuantity()).isEqualTo(gameStateEntity.getStepQuantity());

        // полная проверка маппера player есть в другом классе, а внутри используется этот же маппер
        assertThat(gameState.getNowTurn().getId()).isEqualTo(gameStateEntity.getNowTurn().getId());

        Map<Long, Long> entityIdPairs = gameStateEntity.getPlayerQueuePointers().stream()
                .collect(Collectors.toMap(
                        pointer -> pointer.getPointingPlayer().getId(),
                        pointer -> pointer.getPointedAtPlayer().getId())
                );

        for (Map.Entry<AbstractPlayer, AbstractPlayer> pair : gameState.getPlayersTurn().getPairs().entrySet())
        {
            Long pointingId = pair.getKey().getId();
            Long pointedAtId = pair.getValue().getId();

            assertTrue(entityIdPairs.containsKey(pointingId));
            assertThat(entityIdPairs.get(pointingId)).isEqualTo(pointedAtId);
        }
    }


    @Test
    public void shouldMapGameStateToGameStateEntity()
    {
        LoopingListImpl<AbstractPlayer> loopingList = new LoopingListImpl<>(CreationUtils.createPlayers());
        loopingList.remove(new UserRefPlayer(2L, null, null));

        DbRefGameState gameState = new DbRefGameState(
                loopingList,
                CreationUtils.createCards(),
                Collections.emptyList(),
                loopingList.getCurrent(),
                2,
                1L
        );

        GameStateEntity dbGameState = gameStateRepository.findById(1L).get();

        List<PlayerQueuePointer> oldPointers = dbGameState.getPlayerQueuePointers().stream()
                .map(pointer -> new PlayerQueuePointer(pointer.getId(),
                        new PlayerEntity(pointer.getPointingPlayer().getUser(), pointer.getPointingPlayer().getCards(), pointer.getPointingPlayer().getId()),
                        new PlayerEntity(pointer.getPointedAtPlayer().getUser(), pointer.getPointedAtPlayer().getCards(), pointer.getPointedAtPlayer().getId()))
                )
                .toList();
        Set<Long> oldPointersId = oldPointers.stream()
                .map(PlayerQueuePointer::getId)
                .collect(Collectors.toSet());

        Long oldId = dbGameState.getId();

        GameStateEntity gameStateEntity = gameStateToEntity.map(gameState);

        assertThat(Arrays.stream(gameStateEntity.getCardDeck().getValue())).containsExactlyInAnyOrder(
                CreationUtils.createCards().stream()
                        .map(Card::getName)
                        .toArray(CardName[]::new)
        );
        assertThat(gameStateEntity.getCardReset().getValue()).isEmpty();
        assertThat(gameStateEntity.getId()).isEqualTo(oldId);
        assertThat(gameStateEntity.getNowTurn().getId()).isEqualTo(loopingList.getCurrent().getId());


        for (PlayerQueuePointer pointer : gameStateEntity.getPlayerQueuePointers())
        {
            assertTrue(oldPointersId.contains(pointer.getId()));
        }

    }

}
