package com.kittens.server.integration.mapper;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.server.entity.GameStateEntity;
import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.entity.PlayerQueuePointer;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.integration.IntegrationTest;
import com.kittens.server.mapper.RoomGsToGsEntity;
import com.kittens.server.mapper.GsEntityToRoomGs;
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
    private final GsEntityToRoomGs entityToGameState;
    private final RoomGsToGsEntity gameStateToEntity;

    private final GameStateRepository gameStateRepository;


    @Test
    public void shouldMapGameStateEntityToGameState()
    {
        GameStateEntity gameStateEntity = gameStateRepository.findById(1L).get();

        RoomGameState gameState = entityToGameState.map(gameStateEntity);

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
        loopingList.assignAWalker(new UserRefPlayer(2L, CreationUtils.createCards(), 1L));
        loopingList.remove(new UserRefPlayer(2L, null, null));

        RoomGameState modifiedGameState = new RoomGameState(
                loopingList,
                CreationUtils.createCards(),
                Collections.emptyList(),
                2,
                1L
        );

        GameStateEntity gameState = gameStateRepository.findById(1L).get();

        List<PlayerQueuePointer> oldPointers = gameState.getPlayerQueuePointers().stream()
                .map(pointer -> new PlayerQueuePointer(pointer.getId(),
                        new PlayerEntity(pointer.getPointedAtPlayer().getRoom(), pointer.getPointingPlayer().getUser(), pointer.getPointingPlayer().getCards(), pointer.getPointingPlayer().getId()),
                        new PlayerEntity(pointer.getPointedAtPlayer().getRoom(), pointer.getPointedAtPlayer().getUser(), pointer.getPointedAtPlayer().getCards(), pointer.getPointedAtPlayer().getId()))
                )
                .toList();
        Set<Long> oldPointersId = oldPointers.stream()
                .map(PlayerQueuePointer::getId)
                .collect(Collectors.toSet());

        Long oldId = gameState.getId();

         gameStateToEntity.copy(modifiedGameState, gameState);

        assertThat(Arrays.stream(gameState.getCardDeck().getValue())).containsExactlyInAnyOrder(
                CreationUtils.createCards().stream()
                        .map(Card::getName)
                        .toArray(CardName[]::new)
        );
        assertThat(gameState.getCardReset().getValue()).isEmpty();
        assertThat(gameState.getId()).isEqualTo(oldId);
        assertThat(gameState.getNowTurn().getId()).isEqualTo(loopingList.getCurrent().getId());


        for (PlayerQueuePointer pointer : gameState.getPlayerQueuePointers())
        {
            assertTrue(oldPointersId.contains(pointer.getId()));
        }

    }

}
