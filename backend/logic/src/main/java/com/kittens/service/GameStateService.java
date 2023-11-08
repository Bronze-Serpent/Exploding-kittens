package com.kittens.service;

import com.kittens.GameState;
import com.kittens.card.Card;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class GameStateService
{

    private final CardHandler cardHandler;
    private final CombinationHandler combinationHandler;


    public void addNewCardToPlayer(GameState gameState)
    {
        var movesPlayer = gameState.getNowTurn();

        var receivedCard = gameState.getCardDeck().remove(gameState.getCardDeck().size() - 1);
        movesPlayer.addCard(receivedCard);
        var gettingAction = receivedCard.getGettingAction();
        gettingAction.doAction(gameState);
    }


    public void changeMove(GameState newGameState)
    {
        if (newGameState.getStepQuantity() == 1)
            newGameState.setNowTurn(newGameState.getPlayersTurn().next());
        else
            newGameState.setStepQuantity(newGameState.getStepQuantity() - 1);
    }


    public void playCard(GameState gameState, Card playerCard, List<Card> suddenCards)
    {
        cardHandler.playCard(gameState, playerCard, suddenCards);
        gameState.getNowTurn().removeCard(playerCard.getName());
    }


    public void playCombination(GameState gameState, List<Card> combination)
    {
        combinationHandler.playCombination(gameState, combination);

        var nowTurn = gameState.getNowTurn();
        for (Card combCard : combination)
            nowTurn.removeCard(combCard.getName());
    }
}
