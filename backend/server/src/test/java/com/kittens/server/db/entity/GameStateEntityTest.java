package com.kittens.server.db.entity;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.entity.*;
import com.kittens.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class GameStateEntityTest extends DatabaseTest
{
    private final TestEntityManager entityManager;

    @Test
    void readGameState()
    {
        GameStateEntity gameStateEntity = entityManager.find(GameStateEntity.class, 1);

        assertThat(gameStateEntity).isNotNull();

        assertThat(gameStateEntity.getStepQuantity()).isEqualTo(1);

        CardDeck cardDeck = gameStateEntity.getCardDeck();
        assertThat(cardDeck.getValue()).containsExactlyElementsOf(List.of("TACOCAT", "BEARDCAT", "NYAN_CAT", "DEFUSE"));

        CardReset cardReset = gameStateEntity.getCardReset();
        assertThat(cardReset.getValue()).hasSize(0);

        assertThat(gameStateEntity.getNowTurn().getId()).isEqualTo(1);

        assertThat(gameStateEntity.getPlayerQueuePointers().stream()
                .collect(Collectors.toMap(pointer -> pointer.getPointingPlayer().getId(), pointer -> pointer.getPointedAtPlayer().getId())))
                .isEqualTo(Map.of(
                        1L, 2L,
                        2L, 3L,
                        3L, 1L));
    }


    @Test
    void writeGameState()
    {
        // TODO: 12.12.2023 коммент ниже
        /*
            т.е. мы должны иметь сохранённого user - логично.

            но чтобы сохранить gameState нужно сохранить player сначала

            и чтобы сохранить gameState нужно сначала сохранить деку и сброс.

            Это нормально?
         */

        GameStateEntity gameStateEntity = new GameStateEntity();

        User user = entityManager.find(User.class, 1L);
        
        PlayerEntity player1 = new PlayerEntity();
        player1.setUser(user);
        PlayerEntity player2 = new PlayerEntity();
        player2.setUser(user);

        entityManager.persist(player1);
        entityManager.persist(player2);

        String[] cardDeckVal = {"BEARDCAT", "BEARDCAT", "BEARDCAT", "NYAN_CAT"};
        String[] cardResetVal = {"TACOCAT", "TACOCAT", "BEARDCAT"};

        CardDeck cardDeck = entityManager.persist(new CardDeck(null, cardDeckVal));
        CardReset cardReset = entityManager.persist(new CardReset(null, cardResetVal));


        List<PlayerQueuePointer> playerQueuePointers = List.of
                (new PlayerQueuePointer(null, player1, player2),
                new PlayerQueuePointer(null, player2, player1)
                );

        gameStateEntity.setCardDeck(cardDeck);
        gameStateEntity.setCardReset(cardReset);
        gameStateEntity.setNowTurn(player1);
        gameStateEntity.setStepQuantity(2);
        gameStateEntity.setPlayerQueuePointers(playerQueuePointers);

        entityManager.persist(gameStateEntity);

        GameStateEntity readGameState = entityManager.find(GameStateEntity.class, 2L);
        assertThat(readGameState).isNotNull();
        assertThat(readGameState.getNowTurn()).isEqualTo(player1);
        assertThat(readGameState.getCardDeck().getValue()).isEqualTo(cardDeckVal);
        assertThat(readGameState.getCardReset().getValue()).isEqualTo(cardResetVal);
        assertThat(readGameState.getStepQuantity()).isEqualTo(2);
        assertThat(readGameState.getPlayerQueuePointers()).containsExactlyElementsOf(playerQueuePointers);
    }
}
