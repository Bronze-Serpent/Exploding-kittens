package com.kittens.service;

import com.kittens.Player;
import com.kittens.Utils;
import com.kittens.action.Inaction;
import com.kittens.action.SkippingMove;
import com.kittens.action.sudden.Cancel;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardHandlerImplTest
{

    CardHandler cardHandler = new CardHandlerImpl();

    @Test
    public void shouldPlayCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nexPlayer = gameState.getPlayerById(2L);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var getLost = new SkippingMove();
        var card = new OrdinaryCard("get lost", inaction, getLost, suddenInaction);

        cardHandler.playCard(gameState, card, Collections.emptyList());

        assertEquals(gameState.getNowTurn(), nexPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        oldGameState.addToCardReset(card);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }

    @Test
    public void shouldNotPlayCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var getLost = new SkippingMove();
        var card = new OrdinaryCard("get lost", inaction, getLost, suddenInaction);
        var cancel = new Cancel();
        var no = new OrdinaryCard("no", inaction, inaction, cancel);

        cardHandler.playCard(gameState, card, List.of(no));

        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        oldGameState.addToCardReset(card);
        oldGameState.addToCardReset(no);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }

    @Test
    public void shouldPlayCardWith2Cancel()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nexPlayer = gameState.getPlayerById(2L);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var getLost = new SkippingMove();
        var card = new OrdinaryCard("get lost", inaction, getLost, suddenInaction);
        var cancel = new Cancel();
        var no = new OrdinaryCard("no", inaction, inaction, cancel);

        cardHandler.playCard(gameState, card, List.of(no, no));

        assertEquals(gameState.getNowTurn(), nexPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        oldGameState.addToCardReset(card);
        oldGameState.addToCardReset(no);
        oldGameState.addToCardReset(no);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
