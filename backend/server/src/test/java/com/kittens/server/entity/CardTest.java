package com.kittens.server.entity;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.kittens.logic.card.CardName.DEFUSE;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
// На данный момент не будет функционала для того, чтобы пользователь мог сам конфигурировать игру и т.д.
// так что записывать ничего не нужно в эти таблицы. Запись в таблицы и не тестируется
public class CardTest extends DatabaseTest
{
    private final TestEntityManager entityManager;

    @Test
    void findById()
    {
        CardEntity cardEntity = entityManager.find(CardEntity.class, 2);

        assertThat(cardEntity).isNotNull();
        assertThat(cardEntity.getName()).isEqualTo(DEFUSE);
        assertThat(cardEntity.getGettingAction().getName()).isEqualTo("inaction");
        assertThat(cardEntity.getPlayingAction().getName()).isEqualTo("inaction");
        assertThat(cardEntity.getSuddenAction().getName()).isEqualTo("sudden_inaction");
    }
}
