package com.kittens.action;

import com.kittens.GameState;


public class TransferringMove implements Action
{

    @Override
    public GameState doAction(GameState gameState)
    {
        return new GameState(gameState.getPlayersList(),
                gameState.getCardDeck(),
                gameState.getCardReset(),
                gameState.getPlayersList().next(),
                gameState.getStepQuantity() + 1);
    }
}
