package com.kittens.action;

import com.kittens.Utils;
import com.kittens.action.player.interaction.PlayerQuestioner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class ShuffleTest
{
    @Mock
    private PlayerQuestioner playerQuestioner;

    @InjectMocks
    private Shuffle shuffle;


    @Test
    public void shouldSkipMove()
    {
        doReturn(PlayerQuestioner.ShuffleAnswer.FIRST_CARD_TO_LAST.getAnswer())
                .when(playerQuestioner).ask(1L, PlayerQuestioner.Question.HOW_TO_SHUFFLE);
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        var oldCardDeck = oldGameState.getCardDeck();

        shuffle.doAction(gameState);

        Collections.swap(oldCardDeck, 0, oldCardDeck.size() - 1);
        assertThat(gameState.getCardDeck()).isEqualTo(oldCardDeck);

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
