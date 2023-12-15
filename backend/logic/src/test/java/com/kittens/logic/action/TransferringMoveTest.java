package com.kittens.logic.action;

import com.kittens.logic.models.AbstractPlayer;
import com.kittens.logic.models.Player;
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
        AbstractPlayer nextPlayer = gameState.getPlayerById(2);

        transferringMove.doAction(gameState);

        assertEquals(gameState.getNowTurn(), nextPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(2);

        assertThat(gameState.getPlayersTurn().getSequence()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSequence());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }

}