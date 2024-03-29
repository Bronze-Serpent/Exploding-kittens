package com.kittens.logic.service;


import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameStateImpl;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.logic.model.Player;
import com.kittens.logic.Utils;
import com.kittens.logic.action.Inaction;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GameStateImplUtilsTest
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
                EXPLODING_KITTEN, new ArrayList<>(List.of(explodingKitten, explodingKitten, explodingKitten, explodingKitten)));
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(cards.get(GET_LOST));
        allCards.addAll(cards.get(ATTACK));
        allCards.addAll(cards.get(HAIRY_CATATO));
        allCards.addAll(cards.get(DEFUSE));
        allCards.addAll(cards.get(EXPLODING_KITTEN));

        Player firstPlayer = new Player(3L, new ArrayList<>());
        List<AbstractPlayer> players = List.of(new Player(1L, new ArrayList<>()), new Player(2L, new ArrayList<>()), firstPlayer);
        int numOfCards = 2;

        GameStateImpl generatedGameState = new GameStateImpl(null, null, null, 0);
        gameStateUtils.initGame(allCards,
                players,
                numOfCards,
                firstPlayer,
                generatedGameState,
                new LoopingListImpl<>(players));

        for (AbstractPlayer player : generatedGameState.getPlayersTurn().getElements())
        {
            assertThat(player.getCards()).contains(defuse);
            assertThat(player.getCards()).hasSize(numOfCards);
        }

        assertThat(generatedGameState.getCardDeck().stream()
                .filter(card -> card.getName() == EXPLODING_KITTEN)
                .toList()).hasSize(players.size() - 1);

        assertThat(generatedGameState.getCardDeck().stream()
                .filter(card -> card.getName() == DEFUSE)
                .toList()).hasSize(cards.get(DEFUSE).size() - players.size());

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

        gameStateUtils.addNewCardToPlayer(gameState, gameState.getNowTurn().getId());


        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());

        gameState.getPlayerById(1L).removeCard(TACOCAT);
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
    }

    @Test
    public void shouldSelectNewUser()
    {
        var gameState = Utils.createGameState();
        Utils.set2PlayersWithCards(gameState);
        var oldGameState = Utils.copy(gameState);
        AbstractPlayer nextPlayer = gameState.getPlayerById(2L);

        gameStateUtils.changeMove(gameState);

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), nextPlayer);
        assertThat(gameState.getCardReset()).isEqualTo(oldGameState.getCardReset());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
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
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());
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

        gameStateUtils.playCard(gameState, gameState.getNowTurn().getId(), card, Collections.emptyMap());

        assertThat(gameState.getStepQuantity()).isEqualTo(oldGameState.getStepQuantity());
        assertEquals(gameState.getNowTurn(), oldGameState.getNowTurn());
        assertThat(gameState.getCardDeck()).isEqualTo(oldGameState.getCardDeck());
        assertThat(gameState.getPlayersTurn().getElements()).containsExactlyElementsOf(oldGameState.getPlayersTurn().getElements());

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

        assertThrows(RuntimeException.class, () -> gameStateUtils.playCombination(gameState, gameState.getNowTurn().getId(), List.of(card, card2)));
    }
}
