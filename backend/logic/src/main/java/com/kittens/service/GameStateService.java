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

        var receivedCard = gameState.getCardDeck().remove(0);
        movesPlayer.addCard(receivedCard);
        var gettingAction = receivedCard.getGettingAction();
        gettingAction.doAction(gameState);
    }


    public void changeMove(GameState newGameState)
    {
        if (newGameState.getStepQuantity() == 1)
            newGameState.setNowTurn(newGameState.getPlayersList().next());
        else
            newGameState.setStepQuantity(newGameState.getStepQuantity() - 1);
    }


    public GameState playCard(GameState oldGameState, Card playerCard, List<Card> suddenCards)
    {
        return cardHandler.playCard(oldGameState, playerCard, suddenCards);
    }


    public GameState playCombination(GameState oldGameState, List<Card> combination)
    {
        return combinationHandler.playCombination(oldGameState, combination);
    }
}
