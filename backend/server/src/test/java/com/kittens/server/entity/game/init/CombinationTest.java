package com.kittens.server.entity.game.init;

import com.kittens.server.entity.DatabaseTest;
import com.kittens.server.entity.game.init.CombinationEntity;
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
        assertThat(combinationEntity.getName()).isEqualTo("two_identical");
        assertThat(combinationEntity.getAction().getName()).isEqualTo("steal_unknown_card");
        assertThat(combinationEntity.getPredicate()).isEqualTo(TWO_IDENTICAL);
        assertThat(combinationEntity.getIsEnabled()).isTrue();
    }
}
