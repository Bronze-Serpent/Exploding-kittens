package com.kittens.server.db.repository;

import com.kittens.server.db.DatabaseTest;
import com.kittens.server.entity.game.init.ActionEntity;
import com.kittens.server.repository.game.init.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class GameInitRepositoryTest extends DatabaseTest
{
    private final ActionRepository actionRepository;

    // Тест успешного подставления реализаций и ожидаемого поведения.
    // Во всех repository для инициализации игры один и тот же метод так что тест просто на примере только одного Repository
    @Test
    public void actionRepositoryRead()
    {
        List<ActionEntity> actions = actionRepository.findAll();

        assertThat(actions.stream()
                .map(ActionEntity::getName)).containsExactlyInAnyOrderElementsOf(List.of("explode_or_defuse", "inaction", "peek",
                "shuffle", "skipping_move", "steal_card_from_reset", "steal_known_card", "steal_of_player_choice", "steal_unknown_card",
                "transferring_move"));
    }

}
