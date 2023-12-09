package com.kittens.server.entity;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kittens.logic.card.CardName.*;
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

        List<CardInfo> cardDeck = gameStateEntity.getCardDeck();
        assertThat(cardDeck.stream()
                .map(CardInfo::getCard)
                .map(CardEntity::getName)
                .toList())
                .containsExactlyInAnyOrderElementsOf(List.of(ATTACK, EXPLODING_KITTEN));
        assertThat(cardDeck.stream()
                .map(CardInfo::getQuantity)
                .toList()).containsExactlyInAnyOrderElementsOf(List.of(2, 2));

        List<CardInfo> cardReset = gameStateEntity.getCardReset();
        assertThat(cardReset.stream()
                .map(CardInfo::getCard)
                .map(CardEntity::getName)
                .toList())
                .containsExactlyInAnyOrderElementsOf(List.of(DEFUSE));
        assertThat(cardReset.stream()
                .map(CardInfo::getQuantity)
                .toList()).containsExactlyInAnyOrderElementsOf(List.of(4));

        assertThat(gameStateEntity.getNowTurn().getId()).isEqualTo(1);

        assertThat(gameStateEntity.getPlayerQueuePointers().stream()
                .collect(Collectors.toMap(pointer -> pointer.getPointingPlayer().getId(), pointer -> pointer.getPointedAtPlayer().getId())))
                .isEqualTo(Map.of(
                        1, 2,
                        2, 3,
                        3, 1));
    }


//    @Test
//    void writeGameState()
//    {
//    }
}
