package com.kittens.server.db.entity;

import com.kittens.logic.card.CardName;
import com.kittens.server.db.DatabaseTest;
import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static com.kittens.logic.card.CardName.*;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class PlayerEntityTest extends DatabaseTest
{
    private final TestEntityManager entityManager;

    @Test
    public void readPlayerEntity()
    {
        PlayerEntity playerEntity = entityManager.find(PlayerEntity.class, 1L);

        assertThat(playerEntity.getUser()).isEqualTo(entityManager.find(User.class, 1L));
        assertThat(playerEntity.getCards()).containsExactlyElementsOf(List.of(DEFUSE, FAVOR, HAIRY_CATATO));
    }


    @Test
    public void writePlayerEntity()
    {
        User user = entityManager.find(User.class, 1L);
        CardName[] cards = {BEARDCAT, DEFUSE, TACOCAT, NYAN_CAT};

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUser(user);
        playerEntity.setCards(cards);
        entityManager.persist(playerEntity);

        assertThat(playerEntity.getUser()).isEqualTo(entityManager.find(User.class, 1L));
        assertThat(playerEntity.getCards()).containsExactlyElementsOf(List.of(cards));
    }
}
