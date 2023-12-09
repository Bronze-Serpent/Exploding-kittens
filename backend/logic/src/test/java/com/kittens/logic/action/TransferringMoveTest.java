package com.kittens.logic.action;

import com.kittens.logic.Player;
import com.kittens.logic.Utils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class TransferringMoveTest
{

    private final TransferringMove transferringMove = new TransferringMove();


    @Test
    public void shouldTransferringMove()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nextPlayer = gameState.getPlayerById(2);

        transferringMove.doAction(gameState);

        assertEquals(gameState.getNowTurn(), nextPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(2);

        assertThat(gameState.getPlayersTurn().getConsistency()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getConsistency());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }

}