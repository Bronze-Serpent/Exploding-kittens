package com.kittens.action;

import com.kittens.GameState;
import com.kittens.Player;


public class SkippingMove implements Action
{

    @Override
    public GameState doAction(GameState gameState)
    {
        Player nowTurn = (gameState.getStepQuantity() == 1) ? gameState.getPlayersList().next() : gameState.getNowTurn();
        int stepQuantity = (gameState.getStepQuantity() == 1) ? 1 : gameState.getStepQuantity() - 1;

        return new GameState(gameState.getPlayersList(),
                gameState.getCardDeck(),
                gameState.getCardReset(),
                nowTurn,
                stepQuantity);
    }
}
