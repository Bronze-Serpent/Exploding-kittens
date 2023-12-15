package com.kittens.logic.action;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.Utils;
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
        AbstractPlayer nexPlayer = gameState.getPlayerById(2);

        skippingMove.doAction(gameState);

        assertEquals(gameState.getNowTurn(), nexPlayer);

        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getSequence()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSequence());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
