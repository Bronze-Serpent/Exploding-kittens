package com.kittens.logic.action;

import com.kittens.logic.models.AbstractPlayer;
import com.kittens.logic.models.GameState;


public class SkippingMove implements Action
{

    @Override
    public void doAction(GameState gameState)
    {
        AbstractPlayer nowTurn = (gameState.getStepQuantity() == 1) ? gameState.getPlayersTurn().next() : gameState.getNowTurn();
        int stepQuantity = (gameState.getStepQuantity() == 1) ? 1 : gameState.getStepQuantity() - 1;

        gameState.setNowTurn(nowTurn);
        gameState.setStepQuantity(stepQuantity);
    }

    @Override
    public String getName() {
        return "skipping_move";
    }
}
