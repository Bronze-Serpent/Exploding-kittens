package com.kittens.service;


import com.kittens.Player;
import com.kittens.Utils;
import com.kittens.action.Inaction;
import com.kittens.action.sudden.SuddenInaction;
import com.kittens.card.CardName;
import com.kittens.card.OrdinaryCard;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.kittens.card.CardName.TACOCAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GameStateServiceTest
{
    private final CardHandler cardHandler = new CardHandlerImpl();
    private final CombinationHandler combinationHandler = new CombinationHandlerImpl(Collections.emptyList());
    private final GameStateService gameStateService = new GameStateService(cardHandler, combinationHandler);

    @Test
    public void shouldAddNewCardToPlayer()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var testCard = new OrdinaryCard(TACOCAT, new Inaction(), new Inaction(), new SuddenInaction());
        gameState.getCardDeck().add(testCard);

        gameStateService.addNewCardToPlayer(gameState);


        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        gameState.getPlayerById(1L).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldSelectNewUser()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nextPlayer = gameState.getPlayerById(2L);

        gameStateService.changeMove(gameState);

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), nextPlayer);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldReduceNumOfMovies()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        gameState.setStepQuantity(3);
        var oldGameState = Utils.copy(gameState);

        gameStateService.changeMove(gameState);

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity() - 1);
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldPlayCard()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var card = new OrdinaryCard(TACOCAT, inaction, inaction, suddenInaction);
        gameState.getPlayerById(1L).getCards().add(card);

        gameStateService.playCard(gameState, card, Collections.emptyList());

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());

        oldGameState.addToCardReset(card);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
    }

    @Test
    public void shouldThrowExceptionWhenPlayCombination()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);

        var inaction = new Inaction();
        var suddenInaction = new SuddenInaction();
        var card = new OrdinaryCard(TACOCAT, inaction, inaction, suddenInaction);
        var card2 = new OrdinaryCard(TACOCAT, inaction, inaction, suddenInaction);
        gameState.getPlayerById(1L).getCards().add(card);
        gameState.getPlayerById(1L).getCards().add(card2);

        assertThrows(RuntimeException.class, () -> gameStateService.playCombination(gameState, List.of(card, card2)));
    }
}
