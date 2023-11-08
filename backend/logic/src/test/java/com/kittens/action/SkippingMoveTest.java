package com.kittens.action;

import com.kittens.Player;
import com.kittens.Utils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class SkippingMoveTest
{

    private final SkippingMove skippingMove = new SkippingMove();


    @Test
    public void shouldSkipMove()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nexPlayer = gameState.getPlayerById(2L);

        skippingMove.doAction(gameState);

        assertEquals(gameState.getNowTurn(), nexPlayer);

        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
