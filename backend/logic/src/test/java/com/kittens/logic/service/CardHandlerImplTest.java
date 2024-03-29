package com.kittens.logic.service;

import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.Utils;
import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.sudden.Cancel;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.OrdinaryCard;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kittens.logic.card.CardName.GET_LOST;
import static com.kittens.logic.card.CardName.NO;
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
        AbstractPlayer nexPlayer = gameState.getPlayerById(2L);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var getLost = new SkippingMove();
        var card = new OrdinaryCard(GET_LOST, inaction, getLost, suddenInaction);

        cardHandler.playCard(gameState, gameState.getNowTurn().getId(), card, Collections.emptyMap());

        assertEquals(gameState.getNowTurn(), nexPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
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
        var card = new OrdinaryCard(GET_LOST, inaction, getLost, suddenInaction);
        var cancel = new Cancel();
        var no = new OrdinaryCard(NO, inaction, inaction, cancel);

        cardHandler.playCard(gameState, 1L, card, Map.of(2L, List.of(no)));

        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
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
        AbstractPlayer nextPlayer = gameState.getPlayerById(2L);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var getLost = new SkippingMove();
        var card = new OrdinaryCard(GET_LOST, inaction, getLost, suddenInaction);
        var cancel = new Cancel();
        var no = new OrdinaryCard(NO, inaction, inaction, cancel);

        nextPlayer.addCard(no);

        cardHandler.playCard(gameState, 1L, card, Map.of(2L, List.of(no, no)));

        assertEquals(gameState.getNowTurn(), nextPlayer);
        assertThat(gameState.getStepQuantity()).isEqualTo(1);
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        oldGameState.addToCardReset(card);
        oldGameState.addToCardReset(no);
        oldGameState.addToCardReset(no);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }
}
