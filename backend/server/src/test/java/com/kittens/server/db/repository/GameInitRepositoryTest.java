package com.kittens.server.db.repository;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.game.initialization.entity.CardEntity;
import com.kittens.server.game.initialization.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kittens.logic.card.CardName.*;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class GameInitRepositoryTest extends DatabaseTest
{
    private final CardRepository cardRepository;

    // Тест успешного подставления реализаций и ожидаемого поведения.
    // Во всех repository для инициализации игры один и тот же метод так что тест просто на примере только одного Repository
    @Test
    public void actionRepositoryRead()
    {
        List<CardEntity> cards = cardRepository.findAll();

        assertThat(cards.stream()
                .map(CardEntity::getName)).containsExactlyInAnyOrderElementsOf(List.of(EXPLODING_KITTEN, DEFUSE, ATTACK,
                FAVOR, NO, GET_LOST, SHUFFLE, SEE_THE_FUTURE, HAIRY_CATATO,
                TACOCAT, BEARDCAT, NYAN_CAT, CATTERMELON));
    }

}
