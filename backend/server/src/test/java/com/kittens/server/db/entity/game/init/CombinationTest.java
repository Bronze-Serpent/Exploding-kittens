package com.kittens.server.db.entity.game.init;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.game.initialization.entity.CombinationEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.kittens.logic.combination.CombinationPredicate.TWO_IDENTICAL;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class CombinationTest extends DatabaseTest
{
    private final TestEntityManager entityManager;

    @Test
    void readCombinationEntity()
    {
        CombinationEntity combinationEntity = entityManager.find(CombinationEntity.class, 1);
        assertThat(combinationEntity).isNotNull();
        assertThat(combinationEntity.getName()).isEqualTo("steal_unknown_card");
        assertThat(combinationEntity.getAction().getName()).isEqualTo("steal_unknown_card");
        assertThat(combinationEntity.getCombPredicate()).isEqualTo(TWO_IDENTICAL);
        assertThat(combinationEntity.getIsEnabled()).isTrue();
    }
}
