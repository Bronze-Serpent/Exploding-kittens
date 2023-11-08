package com.kittens.action;

import com.kittens.Utils;
import com.kittens.action.player.interaction.PlayerInformer;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PeekTest
{

    @Test
    public void shouldReturnFirst3Cards()
    {
        PlayerInformer playerInformer = (playerId, informing, msg) ->
        {
            assertEquals(informing, PlayerInformer.Informing.SHOW_CARDS);
            assertThat(playerId).isEqualTo(1L);
            assertThat(msg).isEqualTo("test1,test2,test3");
        };
        Peek peek = new Peek(playerInformer);

        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        gameState.getCardDeck().add(new OrdinaryCard("test1", inaction, inaction, suddenInaction));
        gameState.getCardDeck().add(new OrdinaryCard("test2", inaction, inaction, suddenInaction));
        gameState.getCardDeck().add(new OrdinaryCard("test3", inaction, inaction, suddenInaction));
        var oldGameState = Utils.copy(gameState);

        peek.doAction(gameState);

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
