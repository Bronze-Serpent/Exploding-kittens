package com.kittens.logic.service;


import com.kittens.logic.GameState;
import com.kittens.logic.Player;
import com.kittens.Utils;
import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.TransferringMove;
import com.kittens.logic.action.sudden.Cancel;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kittens.logic.card.CardName.*;
import static com.kittens.logic.card.CardName.HAIRY_CATATO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GameStateUtilsTest
{
    private final CardHandler cardHandler = new CardHandlerImpl();
    private final CombinationHandler combinationHandler = new CombinationHandlerImpl(Collections.emptyList());
    private final GameStateUtils gameStateUtils = new GameStateUtils(cardHandler, combinationHandler);


    @Test
    public void shouldCreateInitializationGameState()
    {

        var suddenInaction = new SuddenInaction();
        var inaction = new Inaction();

        var get_lost = new OrdinaryCard(GET_LOST, inaction, inaction, suddenInaction);
        var attack = new OrdinaryCard(ATTACK, inaction, inaction, suddenInaction);
        var defuse = new OrdinaryCard(DEFUSE, inaction, inaction, suddenInaction);
        var hairy_catato = new OrdinaryCard(HAIRY_CATATO, inaction, inaction, suddenInaction);

        OrdinaryCard explodingKitten = new OrdinaryCard(EXPLODING_KITTEN, inaction, inaction, suddenInaction);

        Map<CardName, List<Card>> cards = Map.of(GET_LOST, new ArrayList<>(List.of(get_lost, get_lost, get_lost)),
                ATTACK, new ArrayList<>(List.of(attack, attack)),
                HAIRY_CATATO, new ArrayList<>(List.of(hairy_catato, hairy_catato, hairy_catato, hairy_catato, hairy_catato)),
                DEFUSE, new ArrayList<>(List.of(defuse, defuse, defuse, defuse)),
                EXPLODING_KITTEN, new ArrayList<>(List.of(explodingKitten, explodingKitten)));

        Player firstPlayer = new Player(3);
        List<Player> players = List.of(new Player(1), new Player(2), firstPlayer);
        int numOfCards = 2;

        GameState generatedGameState = gameStateUtils.initGame(cards, players, numOfCards, firstPlayer);

        for (Player player : generatedGameState.getPlayersTurn().getSourceList())
        {
            assertThat(player.getCards()).contains(defuse);
            assertThat(player.getCards()).hasSize(numOfCards);
        }

        assertThat(generatedGameState.getCardDeck().stream()
                .filter(card -> card.getName() == EXPLODING_KITTEN)
                .toList()).hasSize(cards.get(EXPLODING_KITTEN).size());

        assertThat(generatedGameState.getCardDeck().stream()
                .filter(card -> card.getName() == DEFUSE)
                .toList()).hasSize(cards.get(DEFUSE).size());

        assertThat(generatedGameState.getNowTurn()).isEqualTo(firstPlayer);
        assertThat(generatedGameState.getStepQuantity()).isEqualTo(1);
    }

    @Test
    public void shouldAddNewCardToPlayer()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);

        var testCard = new OrdinaryCard(TACOCAT, new Inaction(), new Inaction(), new SuddenInaction());
        gameState.getCardDeck().add(testCard);

        gameStateUtils.addNewCardToPlayer(gameState);


        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        gameState.getPlayerById(1).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getSourceList()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getSourceList());
    }

    @Test
    public void shouldSelectNewUser()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        Player nextPlayer = gameState.getPlayerById(2);

        gameStateUtils.changeMove(gameState);

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

        gameStateUtils.changeMove(gameState);

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
        gameState.getPlayerById(1).getCards().add(card);

        gameStateUtils.playCard(gameState, card, Collections.emptyList());

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
        gameState.getPlayerById(1).getCards().add(card);
        gameState.getPlayerById(1).getCards().add(card2);

        assertThrows(RuntimeException.class, () -> gameStateUtils.playCombination(gameState, List.of(card, card2)));
    }
}
